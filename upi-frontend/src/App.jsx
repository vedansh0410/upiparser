import { useState, useEffect, useCallback } from "react";
import "./App.css";

// Using relative path — Vite proxy forwards /api → http://localhost:8080/api
const API = "/api/transactions";

/* ─── Sub-components ─── */

function StatCard({ label, value, color, icon }) {
  return (
    <div className="stat-card" style={{ borderColor: color + "44" }}>
      <div className="stat-icon">{icon}</div>
      <div className="stat-value" style={{ color }}>{value}</div>
      <div className="stat-label">{label}</div>
    </div>
  );
}

function Toast({ msg, type, onClose }) {
  useEffect(() => {
    const t = setTimeout(onClose, 3500);
    return () => clearTimeout(t);
  }, [onClose]);
  return (
    <div className={`toast toast-${type}`}>
      <span className="toast-icon">{type === "success" ? "✓" : "✗"}</span>
      {msg}
    </div>
  );
}

function TransactionRow({ t, i }) {
  const d = new Date(t.timestamp);
  const initial = (t.merchant || "?")[0].toUpperCase();
  return (
    <div className={`txn-row ${i % 2 !== 0 ? "alt" : ""}`}>
      <div className="txn-avatar">{initial}</div>
      <div className="txn-info">
        <div className="txn-merchant">{t.merchant || "Unknown"}</div>
        <div className="txn-sms">{t.smsText}</div>
      </div>
      <div className="txn-right">
        <div className="txn-amount">₹{Number(t.amount).toFixed(2)}</div>
        <div className="txn-date">{d.toLocaleDateString("en-IN")}</div>
      </div>
    </div>
  );
}

function MerchantBar({ name, count, max }) {
  const pct = Math.round((count / max) * 100);
  return (
    <div className="merchant-bar">
      <div className="merchant-bar-header">
        <span>{name}</span>
        <span className="merchant-count">{count}</span>
      </div>
      <div className="bar-track">
        <div className="bar-fill" style={{ width: pct + "%" }} />
      </div>
    </div>
  );
}

/* ─── Sample SMS for quick testing ─── */
const SAMPLE_SMS = [
  "Debited ₹500 to Amazon via PhonePe",
  "Paid ₹1500 to Swiggy via Google Pay",
  "Sent ₹800 to Zomato via BHIM UPI",
  "₹299 debited to Netflix via ICICI UPI",
];

const TABS = [
  { id: "parse", label: "📥 Parse SMS" },
  { id: "transactions", label: "📋 Transactions" },
  { id: "analytics", label: "📊 Analytics" },
];

/* ─── Main App ─── */
export default function App() {
  const [sms, setSms] = useState("");
  const [loading, setLoading] = useState(false);
  const [toast, setToast] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [total, setTotal] = useState(0);
  const [merchants, setMerchants] = useState({});
  const [count, setCount] = useState(0);
  const [tab, setTab] = useState("parse");
  const [fetchError, setFetchError] = useState(false);

  const showToast = useCallback((msg, type) => setToast({ msg, type }), []);

  const fetchAll = useCallback(async () => {
    try {
      const [tRes, spendRes, mRes, cRes] = await Promise.all([
        fetch(API),
        fetch(`${API}/total`),
        fetch(`${API}/merchant-count`),
        fetch(`${API}/count`),
      ]);
      const tData = await tRes.json();
      const spendData = await spendRes.json();
      const mData = await mRes.json();
      const cData = await cRes.json();
      setTransactions(tData.data || []);
      setTotal(typeof spendData === "number" ? spendData : 0);
      setMerchants(mData || {});
      setCount(cData.data || 0);
      setFetchError(false);
    } catch (e) {
      console.error("Backend fetch failed:", e);
      setFetchError(true);
    }
  }, []);

  useEffect(() => { fetchAll(); }, [fetchAll]);

  const handleParse = async () => {
    if (!sms.trim()) return;
    setLoading(true);
    try {
      const res = await fetch(`${API}/parse`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ smsText: sms }),
      });
      const data = await res.json();
      if (data.success) {
        showToast(`✅ ₹${data.data?.amount} → ${data.data?.merchant}`, "success");
        setSms("");
        await fetchAll();
        setTab("transactions");
      } else {
        showToast(data.message || "Could not parse SMS", "error");
      }
    } catch {
      showToast("Backend unreachable — make sure Spring Boot is on :8080", "error");
    }
    setLoading(false);
  };

  const maxM = Math.max(...Object.values(merchants), 1);
  const topMerchants = Object.entries(merchants)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 7);

  return (
    <div className="app">

      {/* ── Backend warning banner ── */}
      {fetchError && (
        <div className="warn-banner">
          ⚠️ Cannot reach backend at <code>localhost:8080</code>. Start your Spring Boot app first.
        </div>
      )}

      {/* ── Header ── */}
      <div className="header">
        <div className="logo">₹</div>
        <div>
          <h1>UPI Parser</h1>
          <p>SMS Transaction Intelligence</p>
        </div>
        <button className="refresh-btn" onClick={fetchAll} title="Refresh data">⟳</button>
      </div>

      {/* ── Stat cards ── */}
      <div className="stats-row">
        <StatCard
          label="Total Spend"
          value={`₹${Number(total).toLocaleString("en-IN", { maximumFractionDigits: 0 })}`}
          color="#43e97b" icon="💳"
        />
        <StatCard label="Transactions" value={count} color="#6c63ff" icon="📊" />
        <StatCard label="Merchants" value={Object.keys(merchants).length} color="#ff6584" icon="🏪" />
      </div>

      {/* ── Tabs ── */}
      <div className="tabs">
        {TABS.map(t => (
          <button
            key={t.id}
            className={`tab ${tab === t.id ? "active" : ""}`}
            onClick={() => setTab(t.id)}
          >
            {t.label}
          </button>
        ))}
      </div>

      {/* ══ Parse Tab ══ */}
      {tab === "parse" && (
        <div className="panel">
          <div className="panel-label">Paste UPI SMS</div>
          <textarea
            value={sms}
            onChange={e => setSms(e.target.value)}
            placeholder="e.g.  Debited ₹1200 to Swiggy via HDFC UPI"
            rows={4}
          />
          <div className="sample-btns">
            <span className="sample-hint">Quick fill →</span>
            {SAMPLE_SMS.map(s => (
              <button key={s} className="sample-btn" onClick={() => setSms(s)}>
                {s.length > 30 ? s.substring(0, 30) + "…" : s}
              </button>
            ))}
          </div>
          <button
            className="parse-btn"
            onClick={handleParse}
            disabled={loading || !sms.trim()}
          >
            {loading ? (
              <><span className="spinner" /> Parsing…</>
            ) : (
              "Parse Transaction →"
            )}
          </button>
        </div>
      )}

      {/* ══ Transactions Tab ══ */}
      {tab === "transactions" && (
        <div className="panel no-pad">
          <div className="panel-header">
            <span className="panel-label" style={{ marginBottom: 0 }}>All Transactions</span>
            <span className="panel-count">{transactions.length} entries</span>
          </div>
          {transactions.length === 0 ? (
            <div className="empty">No transactions yet. Go to Parse SMS tab to add some.</div>
          ) : (
            [...transactions].reverse().map((t, i) => (
              <TransactionRow key={t.id} t={t} i={i} />
            ))
          )}
        </div>
      )}

      {/* ══ Analytics Tab ══ */}
      {tab === "analytics" && (
        <div className="analytics-grid">
          <div className="panel">
            <div className="panel-label">Top Merchants by Volume</div>
            {topMerchants.length === 0 ? (
              <div className="empty-sm">No data yet.</div>
            ) : (
              topMerchants.map(([name, c]) => (
                <MerchantBar key={name} name={name} count={c} max={maxM} />
              ))
            )}
          </div>
          <div className="panel">
            <div className="panel-label">Spend Summary</div>
            <div className="summary-grid">
              <div className="summary-card">
                <div className="summary-sub">Total Spend</div>
                <div className="summary-val green">
                  ₹{Number(total).toLocaleString("en-IN", { maximumFractionDigits: 2 })}
                </div>
              </div>
              <div className="summary-card">
                <div className="summary-sub">Avg per Txn</div>
                <div className="summary-val purple">
                  ₹{count > 0 ? (total / count).toFixed(2) : "0.00"}
                </div>
              </div>
              <div className="summary-card">
                <div className="summary-sub">Unique Merchants</div>
                <div className="summary-val pink">
                  {Object.keys(merchants).length}
                </div>
              </div>
              <div className="summary-card">
                <div className="summary-sub">Total Transactions</div>
                <div className="summary-val amber">
                  {count}
                </div>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* ── Toast ── */}
      {toast && (
        <Toast msg={toast.msg} type={toast.type} onClose={() => setToast(null)} />
      )}
    </div>
  );
}
