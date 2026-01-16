# iniPerpus - Sistem Manajemen Perpustakaan

**Sistem Manajemen Perpustakaan Komprehensif dengan Presensi Berbasis Pengenalan Wajah**

iniPerpus adalah sistem manajemen perpustakaan modern yang kaya fitur, dibangun dengan Spring Boot dan Python FastAPI. Sistem ini menggabungkan operasi perpustakaan tradisional dengan teknologi pengenalan wajah mutakhir untuk pelacakan kehadiran pengguna yang mulus.

---

## 📋 Daftar Isi

- [Ikhtisar](#ikhtisar)
- [🚀 Panduan Cepat untuk Pemula](#panduan-cepat-untuk-pemula)
- [Fitur Utama](#fitur-utama)
- [Panduan Fitur](#panduan-fitur)
- [Contoh Alur Kerja Lengkap](#contoh-alur-kerja-lengkap)
- [Arsitektur Sistem](#arsitektur-sistem)
- [Memulai](#memulai)

---

## 🎯 Ikhtisar

iniPerpus menyederhanakan manajemen perpustakaan untuk institusi pendidikan dengan menyediakan:

- **Manajemen Pengguna yang Efisien**: Memelihara catatan pengguna yang komprehensif dengan integrasi pengenalan wajah
- **Kontrol Inventaris Buku**: Melacak semua buku, salinan, dan status ketersediaan
- **Manajemen Peminjaman**: Menangani peminjaman dan pengembalian buku dengan pelacakan tanggal jatuh tempo otomatis
- **Presensi Cerdas**: Check-in otomatis menggunakan teknologi pengenalan wajah
- **Akses Aman**: Autentikasi dan otorisasi berbasis peran
- **UI Cantik**: Desain glassmorphism modern dengan tata letak responsif

### Tumpukan Teknologi

| Komponen | Teknologi |
|-----------|------------|
| Backend | Spring Boot 4.0.0, Java 25 |
| Database | MariaDB 10.x+ |
| Frontend | Thymeleaf, JavaScript ES6+, CSS3 |
| Pengenalan Wajah | Python FastAPI, library face_recognition |
| Build Tool | Gradle |

---

## 🚀 Panduan Cepat untuk Pemula

### Persiapan Pertama Kali (Lakukan Sekali Saja)

#### Langkah 1: Install Software yang Diperlukan

**1.1. Install Java Development Kit (JDK)**
- Unduh JDK 25 dari [Oracle](https://www.oracle.com/java/technologies/downloads/) atau gunakan OpenJDK
- Jalankan installer dan ikuti petunjuk di layar
- Verifikasi instalasi dengan membuka Terminal/Command Prompt dan ketik:
  ```bash
  java -version
  ```
- Anda akan melihat versi Java 25 ditampilkan

**1.2. Install Database MariaDB**
- Unduh MariaDB dari [mariadb.org](https://mariadb.org/download/)
- Saat instalasi, ingat **password root** yang Anda buat
- Start service MariaDB setelah instalasi

**1.3. Install Python**
- Unduh Python 3.8+ dari [python.org](https://www.python.org/downloads/)
- **Penting**: Centang "Add Python to PATH" saat instalasi
- Verifikasi dengan mengetik di terminal:
  ```bash
  python --version
  ```

**1.4. Install Git**
- Unduh dari [git-scm.com](https://git-scm.com/downloads)
- Install dengan pengaturan default

#### Langkah 2: Download Proyek

```bash
# Buka Terminal/Command Prompt dan jalankan:
git clone <repository-url>
cd iniPerpus-SpringBoot
```

#### Langkah 3: Setup Database

**3.1. Buka MariaDB Command Line**
- Windows: Cari "MySQL Client" atau "MariaDB" di Start Menu
- Mac/Linux: Buka Terminal dan ketik `mysql -u root -p`
- Masukkan password root Anda

**3.2. Buat Database**
```sql
CREATE DATABASE iniperpus_2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

**3.3. Konfigurasi Koneksi Database**
- Buka file: `src/main/resources/application.properties`
- Temukan dan perbarui baris ini:
  ```properties
  spring.datasource.username=root
  spring.datasource.password=PASSWORD_ANDA_DISINI
  ```
- Ganti `PASSWORD_ANDA_DISINI` dengan password MariaDB Anda
- Simpan file

#### Langkah 4: Setup Layanan Pengenalan Wajah

**4.1. Navigasi ke Folder Layanan Python**
```bash
cd python-face-service
```

**4.2. Buat Virtual Environment**
```bash
# Di Windows:
python -m venv venv
venv\Scripts\activate

# Di Mac/Linux:
python3 -m venv venv
source venv/bin/activate
```

**4.3. Install Dependencies**
```bash
pip install -r requirements.txt
```
*Catatan: Ini mungkin memakan waktu 5-10 menit*

**4.4. Start Layanan Pengenalan Wajah**
```bash
python main.py
```
- Anda akan melihat: `Uvicorn running on http://127.0.0.1:5000`
- **Biarkan jendela terminal ini tetap terbuka**

#### Langkah 5: Start Aplikasi Utama

**5.1. Buka Terminal/Command Prompt BARU**
- Navigasi ke folder proyek:
  ```bash
  cd iniPerpus-SpringBoot
  ```

**5.2. Jalankan Aplikasi**
```bash
# Di Windows:
gradlew.bat bootRun

# Di Mac/Linux:
./gradlew bootRun
```

**5.3. Tunggu Startup**
- Pertama kali mungkin memakan waktu 2-3 menit
- Cari pesan: `Started IniperpusApplication`
- Aplikasi siap ketika Anda melihat: `Tomcat started on port 8080`

#### Langkah 6: Akses Aplikasi

**6.1. Buka Browser Web Anda**
- Pergi ke: `http://localhost:8080`

**6.2. Login**
- **Username**: `admin`
- **Password**: `admin`
- Klik "Login"

🎉 **Selamat! Anda sekarang sudah masuk ke sistem!**

---

### Tugas Pertama Anda (Ikuti Berurutan)

#### Tugas 1: Tambahkan Anggota Pertama (3 menit)

1. **Klik "Pengguna"** di menu atas
2. **Klik tombol "Tambah Pengguna"** (kanan atas)
3. **Isi formulir**:
   - Nama: `Budi Santoso`
   - NIS: `12345`
   - Kelas: `10A`
4. **Klik "Simpan"**
5. ✅ Anda akan melihat Budi Santoso di daftar!

#### Tugas 2: Daftarkan Wajah Anggota (2 menit)

1. **Klik "Presensi"** di menu atas
2. **Klik tombol "Daftarkan Wajah"**
3. **Klik Ikon Kamera** 📷
4. **Izinkan akses kamera** saat browser meminta
5. **Pilih "Budi Santoso"** dari dropdown
6. **Posisikan wajah Anda** di depan kamera
   - Pastikan wajah cukup cahaya
   - Lihat langsung ke kamera
   - Lepas kacamata jika memungkinkan
7. **Klik "Tangkap & Daftarkan"**
8. ✅ Tunggu pesan sukses!

#### Tugas 3: Tambahkan Buku Pertama (2 menit)

1. **Klik "Buku"** di menu atas
2. **Klik tombol "Tambah Buku"**
3. **Isi formulir**:
   - Judul: `Laskar Pelangi`
   - Penulis: `Andrea Hirata`
   - ISBN: `978-9793062792`
   - Total Salinan: `5`
4. **Klik "Simpan"**
5. ✅ Buku ditambahkan! Salinan tersedia: 5/5

#### Tugas 4: Pinjamkan Buku (2 menit)

1. **Klik "Peminjaman"** di menu atas
2. **Klik tombol "Tambah Peminjaman"**
3. **Isi formulir**:
   - Pilih Siswa: `Budi Santoso`
   - Pilih Buku: `Laskar Pelangi`
   - Tanggal Peminjaman: Tanggal hari ini (otomatis terisi)
4. **Klik "Simpan"**
5. ✅ Buku dipinjam! Cek halaman Buku - Salinan tersedia sekarang: 4/5

#### Tugas 5: Tes Pengenalan Wajah (1 menit)

1. **Klik "Presensi"** di menu atas
2. **Klik tombol "Mulai Kamera"**
3. **Tunjukkan wajah Anda** ke kamera
4. ✅ Sistem harus mengenali Anda secara otomatis!
5. **Cek "Check-in Terbaru"** di bawah untuk melihat catatan kehadiran Anda

---

### Penggunaan Harian (Referensi Cepat)

**Memulai Aplikasi:**
1. Start service MariaDB (jika tidak auto-start)
2. Buka Terminal → `cd python-face-service` → `python main.py`
3. Buka Terminal BARU → `cd iniPerpus-SpringBoot` → `./gradlew bootRun`
4. Buka browser → `http://localhost:8080`
5. Login dengan admin/admin

**Tugas Umum:**
- 📝 **Tambah Anggota**: Pengguna → Tambah Pengguna → Isi form → Simpan
- 📸 **Daftar Wajah**: Presensi → Daftarkan Wajah → Pilih pengguna → Tangkap
- 📚 **Tambah Buku**: Buku → Tambah Buku → Isi detail → Simpan
- 📤 **Pinjam Buku**: Peminjaman → Tambah Peminjaman → Pilih pengguna & buku → Simpan
- 📥 **Kembalikan Buku**: Peminjaman → Cari catatan → Klik tombol Kembalikan
- ✅ **Cek Kehadiran**: Presensi → Mulai Kamera → Wajah dikenali

---

### Pemecahan Masalah untuk Pemula

**Masalah: "Port 8080 sudah digunakan"**
- Solusi: Aplikasi lain menggunakan port 8080
- Tutup program lain atau restart komputer Anda

**Masalah: "Tidak dapat terhubung ke database"**
- Cek MariaDB sedang berjalan
- Verifikasi password di `application.properties`
- Pastikan database `iniperpus_2` ada

**Masalah: "Kamera tidak bekerja"**
- Cek izin browser (klik ikon gembok di address bar)
- Pastikan layanan Python berjalan
- Coba browser berbeda (Chrome direkomendasikan)

**Masalah: "Wajah tidak dikenali"**
- Pastikan pencahayaan bagus (wajah terang, tanpa bayangan)
- Lihat langsung ke kamera
- Daftar ulang wajah dalam kondisi lebih baik

**Masalah: "Dependencies Python gagal diinstall"**
- Windows: Install Visual Studio Build Tools
- Mac: Install Xcode Command Line Tools: `xcode-select --install`
- Linux: Install build-essential: `sudo apt-get install build-essential`

---

## ✨ Fitur Utama

### 👥 Manajemen Pengguna

Kelola catatan pengguna secara efisien dengan operasi CRUD yang intuitif.

![Antarmuka Manajemen Pengguna](./images/user-management.png)

**Fitur:**
- Tambahkan pengguna baru dengan NIS (ID Pengguna) dan informasi kelas
- Edit informasi pengguna yang ada melalui dialog modal
- Hapus catatan pengguna dengan pembersihan cascade data terkait
- Pencarian dan penyaringan real-time di semua pengguna
- Paginasi dengan ukuran halaman yang dapat disesuaikan (5, 10, 25 catatan)
- Integrasi langsung dengan sistem pengenalan wajah
- Ikon aksi cepat untuk operasi umum

**Kasus Penggunaan:**
Ketika pengguna baru mendaftar, navigasi ke bagian Pengguna, klik "Tambah Pengguna", masukkan detail mereka (Nama, NIS, Kelas), dan simpan. Sistem secara otomatis mempersiapkan pengguna untuk pendaftaran wajah.

---

### 📚 Manajemen Buku

Pertahankan koleksi buku perpustakaan Anda dengan kontrol inventaris lengkap.

![Antarmuka Manajemen Buku](./images/book-management.png)

**Fitur:**
- Operasi CRUD lengkap untuk catatan buku
- Lacak informasi penting: Judul, Penulis, ISBN, Jumlah Salinan
- Manajemen inventaris cerdas dengan pembaruan ketersediaan otomatis
- Pemantauan real-time salinan yang tersedia vs total
- Edit buku secara inline tanpa meninggalkan halaman
- Perlindungan penghapusan dengan dialog konfirmasi
- Paginasi efisien untuk koleksi besar
- Tampilan jumlah total buku untuk statistik cepat

**Kasus Penggunaan:**
Untuk menambahkan buku baru ke perpustakaan, buka bagian Buku, klik "Tambah Buku", isi detail (Judul, Penulis, ISBN, Total Salinan), dan simpan. Ketika buku dipinjam atau dikembalikan, jumlah salinan yang tersedia diperbarui secara otomatis.

---

### 🔄 Manajemen Peminjaman

Lacak semua transaksi peminjaman dan kelola tanggal jatuh tempo dengan mudah.

![Antarmuka Manajemen Peminjaman](./images/lending-management.png)

**Fitur:**
- Pinjamkan buku kepada pengguna dengan perhitungan tanggal jatuh tempo otomatis
- Proses pengembalian dan pulihkan inventaris secara otomatis
- Lihat semua pinjaman aktif di satu lokasi terpusat
- Periode pinjaman otomatis 14 hari
- Indikator tanggal jatuh tempo visual
- Pencegahan peminjaman ketika tidak ada salinan yang tersedia
- Pembaruan status real-time di seluruh sistem
- Riwayat peminjaman lengkap dengan paginasi
- Tampilan total pinjaman aktif

**Kasus Penggunaan:**
Ketika pengguna ingin meminjam buku, navigasi ke bagian Peminjaman, klik "Tambah Peminjaman", pilih pengguna dan buku, masukkan tanggal peminjaman. Sistem secara otomatis menghitung tanggal jatuh tempo (14 hari kemudian). Ketika pengguna mengembalikan buku, tandai sebagai dikembalikan dan jumlah ketersediaan buku meningkat.

---

### 📸 Presensi Pengguna (Pengenalan Wajah)

Otomatiskan pelacakan kehadiran dengan teknologi pengenalan wajah yang cerdas.

![Antarmuka Pengenalan Wajah](./images/face-recognition.png)

**Fitur:**
- Integrasi webcam langsung untuk pengambilan wajah real-time
- Daftarkan wajah pengguna yang terhubung ke profil mereka
- Pengenalan wajah otomatis untuk check-in instan
- Pencatatan timestamp untuk pelacakan kehadiran
- Daftarkan ulang atau perbarui wajah pengguna yang ada
- Ambang batas kepercayaan 0.6 untuk pencocokan yang akurat
- Lihat check-in terbaru dengan detail pengguna
- Feed video responsif untuk semua ukuran layar
- Umpan balik visual yang jelas dengan alert berkode warna
- Pembersihan otomatis data wajah ketika pengguna dihapus

**Kasus Penggunaan:**
**Pendaftaran:**
1. Buka bagian Presensi
2. Klik "Daftarkan Wajah"
3. Klik ikon kamera untuk memulai
4. Pilih pengguna dari dropdown
5. Izinkan akses kamera saat diminta
6. Posisikan wajah di frame
7. Berhasil didaftarkan

**Check-in:**
1. Buka bagian Presensi
2. Klik "Mulai Kamera"
3. Sistem secara otomatis mengenali wajah yang terdaftar
4. Kehadiran dicatat secara instan dengan timestamp
5. Lihat check-in terbaru di bawah

---

### 🔐 Autentikasi & Otorisasi

Akses aman ke sistem dengan izin berbasis peran.

![Antarmuka Login](./images/login-page.png)

**Fitur:**
- Autentikasi berbasis form yang aman
- Kontrol akses berbasis peran (Admin, User)
- Enkripsi password BCrypt
- Manajemen dan persistensi sesi
- Perlindungan CSRF
- Logout aman dengan dialog konfirmasi
- Pesan error dengan panduan yang membantu

**Kasus Penggunaan:**
1. Di halaman login, masukkan username dan password
2. Sistem memvalidasi kredensial dengan aman
3. Setelah login, peran menentukan fitur yang tersedia
4. Admin dapat mengelola pengguna dan pengaturan sistem
5. Untuk logout, klik tombol logout dan konfirmasi di dialog

---

### 🎨 Antarmuka Pengguna

Rasakan antarmuka modern dan responsif yang dirancang untuk produktivitas.

![Antarmuka Dashboard](./images/dashboard.png)

**Fitur:**
- **Desain Glassmorphism**: Efek kaca buram dengan estetika modern
- **Background Animasi**: Transisi warna gradien yang halus
- **Tata Letak Responsif**: Pengalaman mulus di desktop, tablet, dan mobile
- **Dialog Modal**: Konfirmasi non-intrusif untuk tindakan kritis
- **Paginasi Client-side**: Navigasi cepat tanpa reload halaman
- **Pembaruan Real-time**: Refleksi instan perubahan di antarmuka
- **Ikon Intuitif**: Indikator visual yang jelas untuk tindakan
- **Alert Berkode Warna**: 
  - 🟢 Hijau untuk pesan sukses
  - 🔴 Merah untuk error
  - 🔵 Biru untuk informasi
- **Styling Konsisten**: Bahasa desain terpadu di seluruh
- **Navigasi Dapat Diakses**: Struktur menu yang jelas dan breadcrumb

---

## 🎓 Panduan Fitur

### Cara Menambahkan Pengguna

1. Klik **"Pengguna"** di menu navigasi
2. Klik tombol **"Tambah Pengguna"**
3. Isi field yang diperlukan:
   - **Nama**: Nama lengkap pengguna
   - **NIS**: Nomor identifikasi pengguna
   - **Kelas**: Kelas/tingkat pengguna
4. Klik **"Simpan"**
5. Pengguna muncul di daftar dan siap untuk pendaftaran wajah

### Cara Mendaftarkan Wajah Pengguna

1. Navigasi ke bagian **"Presensi"**
2. Klik tombol **"Daftarkan Wajah"**
3. Klik **Ikon Kamera** untuk mengaktifkan webcam
4. Pilih pengguna dari menu dropdown
5. Pastikan pencahayaan bagus dan posisikan wajah dengan jelas di frame
6. Klik **"Tangkap & Daftarkan"** saat siap
7. Sistem akan memproses dan menyimpan encoding wajah
8. Pesan konfirmasi akan muncul

### Cara Meminjamkan Buku

1. Buka bagian **"Peminjaman"**
2. Klik tombol **"Tambah Peminjaman"**
3. Pilih **Pengguna** dari dropdown
4. Pilih **Buku** dari daftar buku yang tersedia
5. Atur **Tanggal Peminjaman** (biasanya hari ini)
6. Sistem secara otomatis menghitung tanggal jatuh tempo (14 hari)
7. Klik **"Simpan"**
8. Jumlah buku berkurang secara otomatis di inventaris

### Cara Memproses Pengembalian Buku

1. Navigasi ke bagian **"Peminjaman"**
2. Temukan buku yang dikembalikan di daftar pinjaman aktif
3. Klik tombol **Kembalikan** di sebelah catatan peminjaman
4. Konfirmasi pengembalian di dialog
5. Jumlah ketersediaan buku meningkat secara otomatis
6. Catatan peminjaman pindah ke riwayat

### Cara Mencari dan Menyaring

**Untuk Pengguna:**
1. Di bagian Pengguna, cari kotak pencarian
2. Ketik nama pengguna, NIS, atau kelas
3. Hasil diperbarui secara real-time
4. Hapus pencarian untuk melihat semua pengguna

**Untuk Buku:**
1. Di bagian Buku, gunakan fungsionalitas pencarian
2. Masukkan judul buku, penulis, atau ISBN
3. Buku yang cocok ditampilkan secara instan
4. Sesuaikan paginasi untuk melihat lebih banyak hasil

### Cara Memeriksa Catatan Kehadiran

1. Buka bagian **"Presensi"**
2. Lihat **"Check-in Terbaru"** di bagian bawah
3. Setiap catatan menunjukkan:
   - Nama pengguna dan NIS
   - Timestamp check-in
   - Tingkat kepercayaan pencocokan wajah
4. Gunakan paginasi untuk menelusuri riwayat

---

## 📖 Contoh Alur Kerja Lengkap

### Skenario: Hari Biasa di Perpustakaan

Mari ikuti **Bu Sarah (Pustakawan)** melalui hari lengkap menggunakan iniPerpus.

#### 🌅 Pagi - Persiapan (08:00)

**Langkah 1: Start Sistem**
1. Nyalakan komputer
2. Start service MariaDB
3. Buka Terminal 1:
   ```bash
   cd iniPerpus-SpringBoot/python-face-service
   source venv/bin/activate  # atau venv\Scripts\activate di Windows
   python main.py
   ```
4. Buka Terminal 2:
   ```bash
   cd iniPerpus-SpringBoot
   ./gradlew bootRun
   ```
5. Buka browser → `http://localhost:8080`
6. Login: admin / admin

✅ **Sistem Siap!**

---

#### 👤 Pendaftaran Anggota Baru (08:30)

**Siti Nurhaliza bergabung ke perpustakaan hari ini**

**Langkah 2: Tambah Catatan Anggota**
1. Klik menu **"Pengguna"**
2. Klik tombol **"Tambah Pengguna"**
3. Masukkan:
   - Nama: `Siti Nurhaliza`
   - NIS: `2026001`
   - Kelas: `11B`
4. Klik **"Simpan"**

**Langkah 3: Daftarkan Wajah Siti**
1. Klik menu **"Presensi"**
2. Klik tombol **"Daftarkan Wajah"**
3. Klik ikon kamera 📷
4. Pilih **"Siti Nurhaliza"** dari dropdown
5. Minta Siti melihat ke kamera
6. Klik **"Tangkap & Daftarkan"**
7. Tunggu pesan sukses hijau

✅ **Siti sekarang sudah di sistem!**

---

#### 📚 Kedatangan Buku Baru (09:00)

**Perpustakaan menerima 10 buku baru**

**Langkah 4: Tambahkan Buku ke Sistem**
1. Klik menu **"Buku"**
2. Untuk setiap buku, klik **"Tambah Buku"**:

   **Buku 1:**
   - Judul: `Laskar Pelangi`
   - Penulis: `Andrea Hirata`
   - ISBN: `978-9793062792`
   - Total Salinan: `3`
   - Klik **"Simpan"**

   **Buku 2:**
   - Judul: `Bumi Manusia`
   - Penulis: `Pramoedya Ananta Toer`
   - ISBN: `978-9799101891`
   - Total Salinan: `2`
   - Klik **"Simpan"**

   *Ulangi untuk buku sisanya...*

✅ **Semua buku ditambahkan! Total inventaris diperbarui.**

---

#### 📖 Istirahat Pertama - Peminjaman Buku (10:00)

**Beberapa anggota ingin meminjam buku**

**Langkah 5: Pinjamkan Buku ke Siti**
1. Klik menu **"Peminjaman"**
2. Klik tombol **"Tambah Peminjaman"**
3. Pilih:
   - Pengguna: `Siti Nurhaliza`
   - Buku: `Laskar Pelangi`
   - Tanggal Peminjaman: `2026-01-15` (hari ini)
4. Sistem menampilkan: Tanggal Jatuh Tempo: `2026-01-29` (otomatis dihitung)
5. Klik **"Simpan"**

**Langkah 6: Pinjamkan Buku ke Anggota Lain**
1. Klik **"Tambah Peminjaman"** lagi
2. Pilih:
   - Pengguna: `Budi Santoso`
   - Buku: `Bumi Manusia`
   - Tanggal Peminjaman: Hari ini
3. Klik **"Simpan"**

✅ **Buku dipinjam! Cek halaman Buku:**
- Laskar Pelangi: Tersedia 2/3
- Bumi Manusia: Tersedia 1/2

---

#### ✅ Waktu Makan Siang - Pelacakan Kehadiran (12:00)

**Anggota datang ke perpustakaan untuk membaca**

**Langkah 7: Check-in Otomatis Pengenalan Wajah**
1. Klik menu **"Presensi"**
2. Klik tombol **"Mulai Kamera"**
3. Siti berjalan di depan kamera
   - 🎯 Sistem mengenali: "Siti Nurhaliza"
   - ✅ Check-in dicatat: 12:05
4. Budi berjalan di depan kamera
   - 🎯 Sistem mengenali: "Budi Santoso"
   - ✅ Check-in dicatat: 12:06

**Langkah 8: Lihat Kehadiran**
- Scroll ke bawah ke **"Check-in Terbaru"**
- Lihat daftar:
  - Siti Nurhaliza (11B) - 12:05 - Kepercayaan: 95%
  - Budi Santoso (10A) - 12:06 - Kepercayaan: 92%

✅ **Kehadiran otomatis tercatat!**

---

#### 📥 Setelah Sekolah - Pengembalian Buku (15:00)

**Budi mengembalikan bukunya**

**Langkah 9: Proses Pengembalian Buku**
1. Klik menu **"Peminjaman"**
2. Temukan **"Budi Santoso - Bumi Manusia"** di daftar
3. Klik tombol **"Kembalikan"** (ikon ↩️)
4. Konfirmasi di popup: **"Ya, Kembalikan"**

✅ **Buku dikembalikan! Cek halaman Buku:**
- Bumi Manusia: Tersedia 2/2 (kembali ke stok penuh)

---

#### 📊 Akhir Hari - Laporan (16:00)

**Langkah 10: Cek Statistik**

1. **Halaman Pengguna**:
   - Total Anggota: 25
   - Baru Hari Ini: 1 (Siti)

2. **Halaman Buku**:
   - Total Buku: 150
   - Total Salinan: 450
   - Tersedia: 425
   - Dipinjam: 25

3. **Halaman Peminjaman**:
   - Pinjaman Aktif: 24
   - Jatuh Tempo Hari Ini: 0
   - Buku Terlambat: 2 (perlu tindak lanjut)

4. **Halaman Presensi**:
   - Check-in Hari Ini: 45 anggota
   - Waktu Puncak: 12:00-13:00

✅ **Hari selesai! Semua data tersimpan otomatis.**

---

#### 🔒 Penutupan (16:30)

**Langkah 11: Matikan Sistem**
1. Klik tombol **"Logout"**
2. Konfirmasi logout
3. Tutup browser
4. Stop Spring Boot (Ctrl+C di Terminal 2)
5. Stop layanan Python (Ctrl+C di Terminal 1)

✅ **Sistem ditutup dengan aman. Semua data tersimpan untuk besok!**

---

### 💡 Poin Penting dari Alur Kerja Ini

1. **Setup sekali** diperlukan untuk setiap anggota (tambah pengguna + daftar wajah)
2. **Buku otomatis dilacak** - sistem memperbarui ketersediaan secara instan
3. **Tanggal jatuh tempo dihitung otomatis** (14 hari dari tanggal peminjaman)
4. **Pengenalan wajah bekerja tanpa sentuhan** - anggota hanya berjalan ke kamera
5. **Semua data persisten** - tutup dan buka kapan saja, data tetap ada
6. **Perubahan tercermin di mana-mana** - pinjam buku, ketersediaan update di halaman Buku

---

### 🎯 Langkah Selanjutnya untuk Pengguna Baru

**Minggu 1: Operasi Dasar**
- ✅ Tambahkan 10 anggota
- ✅ Daftarkan semua wajah
- ✅ Tambahkan 20 buku
- ✅ Proses 5 peminjaman
- ✅ Tes pengenalan wajah setiap hari

**Minggu 2: Fitur Lanjutan**
- 📝 Edit informasi anggota
- 🔍 Gunakan fitur pencarian/filter
- 📊 Lacak pola kehadiran
- 🔄 Proses pengembalian secara teratur
- 📅 Pantau tanggal jatuh tempo

**Minggu 3: Pemeliharaan**
- 🗑️ Hapus anggota yang tidak aktif
- 📚 Perbarui inventaris buku
- 🔄 Daftar ulang wajah jika diperlukan
- 📈 Tinjau statistik peminjaman
- 🎨 Sesuaikan sesuai kebutuhan

---

## 🏗️ Arsitektur Sistem

### Alur Data

```
┌─────────────────────────────────────────────────────────┐
│                    Sistem iniPerpus                      │
├─────────────────────────────────────────────────────────┤
│                                                           │
│  ┌──────────────┐          ┌────────────────────┐       │
│  │   Frontend   │          │  Aplikasi Spring   │       │
│  │ (Thymeleaf,  │◄────────►│  Boot (REST API,   │       │
│  │ JavaScript)  │ AJAX/JSON│   Logika Bisnis)   │       │
│  └──────────────┘          └────────────────────┘       │
│                                    │                     │
│                                    ▼                     │
│                            ┌─────────────────┐           │
│                            │    MariaDB      │           │
│                            │   Database      │           │
│                            └─────────────────┘           │
│                                                           │
│  ┌──────────────────────┐   ┌────────────────────┐      │
│  │   Kamera/Webcam      │──►│  Python FastAPI    │      │
│  │  (Tangkap Wajah)     │   │  Pengenalan Wajah  │      │
│  └──────────────────────┘   └────────────────────┘      │
│                                    │                     │
│                                    ▼                     │
│                            ┌─────────────────┐           │
│                            │  Face Encodings │           │
│                            │  (Data Wajah)   │           │
│                            └─────────────────┘           │
│                                                           │
└─────────────────────────────────────────────────────────┘
```

### Ikhtisar Komponen

| Komponen | Tujuan |
|-----------|---------|
| **Frontend** | Antarmuka pengguna yang dibangun dengan template Thymeleaf dan JavaScript vanilla |
| **Controllers** | Menangani permintaan HTTP dan mengarahkan ke layanan yang sesuai |
| **Services** | Mengimplementasikan logika bisnis dan mengorkestrasikan operasi |
| **Repositories** | Mengakses database melalui Spring Data JPA |
| **Models** | Mewakili entitas database (User, Book, Lending, dll.) |
| **Layanan Python** | Memproses permintaan pengenalan wajah secara terpisah |
| **Database** | Menyimpan semua data persisten (pengguna, buku, pinjaman, kehadiran) |

---

## 🚀 Memulai

### Prasyarat

- **Java Development Kit (JDK)**: Versi 25 atau kompatibel
- **MariaDB**: Versi 10.x atau lebih tinggi
- **Python**: Versi 3.8 atau lebih tinggi
- **Git**: Untuk kloning repositori
- **Visual Studio Code** atau **IntelliJ IDEA** (IDE yang direkomendasikan)

### Pengaturan Cepat

1. **Klon repositori**
   ```bash
   git clone <repository-url>
   cd iniPerpus-SpringBoot
   ```

2. **Konfigurasi Database**
   ```sql
   CREATE DATABASE iniperpus_2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
   Perbarui `application.properties` dengan kredensial database Anda

3. **Setup Layanan Wajah Python**
   ```bash
   cd python-face-service
   python -m venv venv
   source venv/bin/activate  # Di Windows: venv\Scripts\activate
   pip install -r requirements.txt
   python main.py
   ```

4. **Jalankan Aplikasi Spring Boot**
   ```bash
   ./gradlew bootRun
   ```
   Aplikasi akan tersedia di `http://localhost:8080`

5. **Login**
   - Admin Default: `admin` / `admin`
   - Buat pengguna tambahan sesuai kebutuhan

### Pemecahan Masalah

**Masalah**: Kamera tidak berfungsi di bagian Presensi
- **Solusi**: Pastikan browser memiliki izin kamera yang diaktifkan dan layanan Python berjalan

**Masalah**: Pengenalan wajah tidak cocok
- **Solusi**: Daftarkan wajah di pencahayaan yang baik, pastikan wajah terlihat jelas, daftarkan ulang jika diperlukan

**Masalah**: Peminjaman buku gagal
- **Solusi**: Pastikan pengguna dan buku dipilih, dan salinan yang tersedia > 0

---

## 📊 Skenario Dunia Nyata

### Skenario 1: Hari Pembukaan Sekolah

1. Admin login dan menambahkan semua pengguna dari daftar pendaftaran
2. Mendaftarkan wajah setiap pengguna di bagian Presensi
3. Guru sekarang dapat menggunakan pengenalan wajah untuk kehadiran
4. Buku perpustakaan ditambahkan ke sistem dengan jumlah inventaris
5. Pengguna dapat meminjam buku dengan meminta pustakawan

### Skenario 2: Sirkulasi Buku Mingguan

- Senin: Pengguna meminjam buku (catatan peminjaman dibuat)
- Rabu: Pengenalan wajah memeriksa kehadiran
- Jumat: Beberapa buku dikembalikan (inventaris diperbarui)
- Sistem melacak buku yang terlambat secara otomatis melalui tanggal jatuh tempo

### Skenario 3: Transfer Pengguna

1. Data asli pengguna dihapus dari sistem
2. Encoding wajah dihapus secara otomatis
3. Semua catatan peminjaman dihapus secara cascade
4. Riwayat kehadiran dipertahankan di database
5. Data pengguna dapat ditambahkan kembali jika diperlukan

---

## 💡 Praktik Terbaik

### Manajemen Pengguna
- Jaga informasi pengguna tetap terkini
- Daftarkan wajah segera setelah pendaftaran pengguna
- Daftarkan ulang setiap tahun atau jika pengenalan sering gagal

### Manajemen Buku
- Pertahankan jumlah salinan yang akurat
- Tetapkan jumlah total salinan yang realistis untuk perencanaan inventaris
- Tinjau entri ISBN secara teratur untuk akurasi

### Operasi Peminjaman
- Proses pengembalian dengan segera untuk memperbarui ketersediaan
- Pantau tanggal jatuh tempo untuk mencegah buku terlambat
- Tindak lanjuti buku yang tidak dikembalikan setelah tanggal jatuh tempo

### Pengenalan Wajah
- Daftarkan wajah di area yang cukup cahaya
- Pastikan pencahayaan alami (hindari backlighting)
- Daftarkan hanya satu wajah pengguna pada satu waktu
- Perbarui pendaftaran jika penampilan pengguna berubah secara signifikan

---

## 📞 Dukungan & Umpan Balik

Untuk masalah, saran, atau permintaan fitur:
- Periksa README.md utama untuk dokumentasi teknis terperinci
- Tinjau log aplikasi untuk pesan error
- Pastikan semua layanan (Spring Boot + Python) berjalan

---

## 📄 Lisensi

Proyek ini disediakan apa adanya untuk tujuan pendidikan.

---

**Terakhir Diperbarui**: Januari 2026
**Versi**: 1.0
