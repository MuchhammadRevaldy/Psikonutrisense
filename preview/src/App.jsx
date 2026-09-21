import React, { useState } from 'react';
import { 
  Clock, 
  Smile, 
  Award, 
  QrCode, 
  Check, 
  Syringe, 
  LineChart, 
  X, 
  ChevronRight,
  Wifi,
  Battery,
  Signal
} from 'lucide-react';

export default function App() {
  const [pmtStatus, setPmtStatus] = useState('Habis');
  const [poin, setPoin] = useState(1250);
  const [targets, setTargets] = useState({
    target1: true,
    target2: false,
    target3: false
  });

  const [activeModal, setActiveModal] = useState(null); // 'grafik', 'qr', 'reminder', 'category'
  const [categoryModalContent, setCategoryModalContent] = useState('');

  const handleTargetToggle = (key) => {
    const newState = !targets[key];
    setTargets({ ...targets, [key]: newState });
    if (newState) {
      setPoin(prev => prev + 50);
    } else {
      setPoin(prev => prev - 50);
    }
  };

  const categories = [
    { icon: '🌱', label: 'Tumbuh', desc: 'Pantau Berat & Tinggi Badan' },
    { icon: '🥩', label: 'Makan', desc: 'Asupan Protein & Gizi Harian' },
    { icon: '🛡️', label: 'Imunisasi', desc: 'Jadwal & Riwayat Vaksin' },
    { icon: '🧩', label: 'Psikososial', desc: 'Perkembangan Motorik & Emosi' },
    { icon: '📚', label: 'Edukasi', desc: 'Artikel & Tips Parenting' }
  ];

  return (
    <div className="preview-container">
      {/* Top Outer Status / Controls */}
      <div className="device-header-bar">
        <span>📱 Interactive Android App Preview</span>
        <span>Psikonutrisense v1.0</span>
      </div>

      {/* Smartphone Mockup Container */}
      <div className="mobile-frame">
        {/* Notch */}
        <div className="mobile-notch"></div>

        {/* Status Bar */}
        <div className="phone-status-bar">
          <span>09:41</span>
          <div style={{ display: 'flex', gap: '6px', alignItems: 'center' }}>
            <Signal size={14} />
            <Wifi size={14} />
            <Battery size={16} />
          </div>
        </div>

        {/* Scrollable Screen Content */}
        <div className="screen-content">
          {/* 1. Header Section */}
          <div className="header-row">
            <div>
              <h1 className="greeting-title">
                Halo, Ibunda! <span style={{ fontSize: '22px' }}>👋</span>
              </h1>
              <p className="greeting-subtitle">
                Pantau tumbuh kembang<br />si Kecil setiap hari
              </p>
            </div>
            <button 
              className="icon-btn-circle" 
              onClick={() => alert('Riwayat aktivitas & notifikasi terbaru Ibunda.')}
              title="Riwayat Notifikasi"
            >
              <Clock size={20} />
            </button>
          </div>

          {/* 2. Kondisi Ibu Card */}
          <div className="card-kondisi">
            <div className="kondisi-left">
              <div className="avatar-kondisi-icon">
                <Smile color="#B33B5E" size={26} />
              </div>
              <div>
                <div className="kondisi-label">KONDISI IBU</div>
                <div className="kondisi-status">Tenang & Bahagia</div>
              </div>
            </div>

            <div>
              <div className="badge-lencana">
                <Award size={13} /> Lencana Ibu Aktif
              </div>
              <div className="poin-text">{poin.toLocaleString('id-ID')} Poin</div>
            </div>
          </div>

          {/* 3. Profil Anak Section */}
          <div>
            <h2 className="section-title" style={{ marginBottom: '12px' }}>Profil Anak</h2>
            
            <div className="card-profil-anak">
              <div className="child-avatar">👧</div>
              <div>
                <div className="child-name">Aisyah Putri</div>
                <div className="child-age">2 tahun 3 bulan</div>
              </div>
            </div>

            {/* Quick Categories Bar */}
            <div className="category-row" style={{ marginTop: '16px' }}>
              {categories.map((cat, idx) => (
                <div 
                  key={idx} 
                  className="category-item"
                  onClick={() => {
                    setCategoryModalContent(cat);
                    setActiveModal('category');
                  }}
                >
                  <div className="category-icon-box">{cat.icon}</div>
                  <span className="category-label">{cat.label}</span>
                </div>
              ))}
            </div>
          </div>

          {/* 4. Modul Harian PMT */}
          <div className="card-pmt">
            <div className="pmt-header">
              <h3 style={{ fontSize: '16px', fontWeight: 800, color: 'var(--text-dark-header)' }}>
                Modul Harian PMT
              </h3>
              <div className="scan-qr-btn" onClick={() => setActiveModal('qr')}>
                <QrCode size={16} />
                <span>Scan QR</span>
              </div>
            </div>

            <div className="pmt-subtitle">Konsumsi PMT Hari Ini:</div>

            <div className="pmt-toggle-group">
              {['Habis', 'Sebagian', 'Tidak'].map((opt) => (
                <button
                  key={opt}
                  className={`pmt-toggle-btn ${pmtStatus === opt ? 'active' : ''}`}
                  onClick={() => setPmtStatus(opt)}
                >
                  {opt}
                </button>
              ))}
            </div>
          </div>

          {/* 5. Target Minggu Ini */}
          <div className="target-container">
            <h2 className="section-title">Target Minggu Ini</h2>

            <div 
              className={`checklist-item ${targets.target1 ? 'checked' : ''}`}
              onClick={() => handleTargetToggle('target1')}
            >
              <div className="checkbox-custom">
                {targets.target1 && <Check size={16} strokeWidth={3} />}
              </div>
              <span className="target-text">Berikan Protein Hewani</span>
            </div>

            <div 
              className={`checklist-item ${targets.target2 ? 'checked' : ''}`}
              onClick={() => handleTargetToggle('target2')}
            >
              <div className="checkbox-custom">
                {targets.target2 && <Check size={16} strokeWidth={3} />}
              </div>
              <span className="target-text">Stimulasi Main Bersama</span>
            </div>

            <div 
              className={`checklist-item ${targets.target3 ? 'checked' : ''}`}
              onClick={() => handleTargetToggle('target3')}
            >
              <div className="checkbox-custom">
                {targets.target3 && <Check size={16} strokeWidth={3} />}
              </div>
              <span className="target-text">Hadir Posyandu</span>
            </div>
          </div>

          {/* 6. Status Gizi Card */}
          <div className="card-status-gizi">
            <div>
              <div className="status-gizi-title">Status Gizi</div>
              <div className="status-gizi-value">Normal</div>
              <div className="status-gizi-metric">BB/U: 0,85 (Z-Score 0,2)</div>
              <div className="status-gizi-metric">TB/U: 0,67 (Z-Score 0,1)</div>
            </div>
            <div className="gauge-circle"></div>
          </div>

          {/* 7. Main Button */}
          <button className="cta-grafik-btn" onClick={() => setActiveModal('grafik')}>
            Lihat Grafik Pertumbuhan
          </button>

          {/* 8. Imunisasi Card */}
          <div className="card-imunisasi">
            <div className="imunisasi-header">
              <div className="imunisasi-icon-bg">
                <Syringe size={20} />
              </div>
              <span className="imunisasi-title">Imunisasi</span>
            </div>

            <div className="callout-imunisasi">
              <div className="yellow-bar"></div>
              <div className="callout-content">
                <div className="callout-sub">Selanjutnya (3 hari lagi):</div>
                <div className="callout-main">DPT-HB-Hib 3</div>
              </div>
            </div>

            <button className="btn-detail-reminder" onClick={() => setActiveModal('reminder')}>
              Detail & Reminder
            </button>
          </div>
        </div>

        {/* MODAL DRAWERS */}
        {activeModal === 'grafik' && (
          <div className="modal-overlay" onClick={() => setActiveModal(null)}>
            <div className="modal-content" onClick={e => e.stopPropagation()}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h3 style={{ fontSize: '18px', fontWeight: 800, color: 'var(--text-dark-header)' }}>
                  Grafik Pertumbuhan WHO
                </h3>
                <button 
                  onClick={() => setActiveModal(null)} 
                  style={{ border: 'none', background: 'none', cursor: 'pointer' }}
                >
                  <X size={22} color="#666" />
                </button>
              </div>

              <p style={{ fontSize: '13px', color: '#666' }}>
                Grafik Kurva Berat Badan Menurut Umur (BB/U) & Tinggi Badan (TB/U) Aisyah Putri.
              </p>

              {/* Graphic Mock */}
              <div style={{ 
                height: '180px', 
                background: '#FDF0F3', 
                borderRadius: '16px', 
                display: 'flex', 
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center',
                gap: '8px',
                border: '1.5px dashed var(--primary-rose)'
              }}>
                <LineChart size={48} color="var(--primary-rose)" />
                <span style={{ fontSize: '13px', fontWeight: 700, color: 'var(--primary-rose)' }}>
                  Z-Score Normal (+0.2 SD)
                </span>
              </div>

              <button 
                className="cta-grafik-btn" 
                onClick={() => setActiveModal(null)}
                style={{ height: '44px', fontSize: '14px' }}
              >
                Tutup Preview
              </button>
            </div>
          </div>
        )}

        {activeModal === 'qr' && (
          <div className="modal-overlay" onClick={() => setActiveModal(null)}>
            <div className="modal-content" onClick={e => e.stopPropagation()}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h3 style={{ fontSize: '18px', fontWeight: 800, color: 'var(--text-dark-header)' }}>
                  Scanner QR PMT Posyandu
                </h3>
                <button onClick={() => setActiveModal(null)} style={{ border: 'none', background: 'none' }}>
                  <X size={22} color="#666" />
                </button>
              </div>

              <div style={{
                height: '200px',
                background: '#111',
                borderRadius: '16px',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                color: 'white',
                flexDirection: 'column',
                gap: '12px'
              }}>
                <QrCode size={64} color="#F9D3DF" />
                <span style={{ fontSize: '13px' }}>Arahkan Kamera ke QR Code Kemasan PMT</span>
              </div>
            </div>
          </div>
        )}

        {activeModal === 'reminder' && (
          <div className="modal-overlay" onClick={() => setActiveModal(null)}>
            <div className="modal-content" onClick={e => e.stopPropagation()}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h3 style={{ fontSize: '18px', fontWeight: 800, color: 'var(--text-dark-header)' }}>
                  Jadwal & Pengingat Imunisasi
                </h3>
                <button onClick={() => setActiveModal(null)} style={{ border: 'none', background: 'none' }}>
                  <X size={22} color="#666" />
                </button>
              </div>

              <div style={{ background: '#E3F2FD', padding: '16px', borderRadius: '16px' }}>
                <div style={{ fontWeight: 800, color: '#1565C0', fontSize: '16px' }}>
                  DPT-HB-Hib 3
                </div>
                <div style={{ fontSize: '13px', color: '#1E88E5', marginTop: '4px' }}>
                  Target: 22 September 2026 (3 Hari Lagi)
                </div>
                <div style={{ fontSize: '12px', color: '#555', marginTop: '8px' }}>
                  Posyandu Mawar 04 - Kebayoran Baru
                </div>
              </div>

              <button 
                className="cta-grafik-btn" 
                onClick={() => {
                  alert('Pengingat imunisasi berhasil diset di Kalender!');
                  setActiveModal(null);
                }}
                style={{ height: '44px', fontSize: '14px' }}
              >
                Aktifkan Alarm Pengingat
              </button>
            </div>
          </div>
        )}

        {activeModal === 'category' && (
          <div className="modal-overlay" onClick={() => setActiveModal(null)}>
            <div className="modal-content" onClick={e => e.stopPropagation()}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h3 style={{ fontSize: '18px', fontWeight: 800, color: 'var(--text-dark-header)' }}>
                  {categoryModalContent.icon} Modul {categoryModalContent.label}
                </h3>
                <button onClick={() => setActiveModal(null)} style={{ border: 'none', background: 'none' }}>
                  <X size={22} color="#666" />
                </button>
              </div>

              <p style={{ fontSize: '14px', color: '#444' }}>
                {categoryModalContent.desc}
              </p>

              <button 
                className="cta-grafik-btn" 
                onClick={() => setActiveModal(null)}
                style={{ height: '44px', fontSize: '14px' }}
              >
                Buka Modul
              </button>
            </div>
          </div>
        )}

      </div>
    </div>
  );
}
