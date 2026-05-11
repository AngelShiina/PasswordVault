# 🔐 PasswordVault

A **local, offline password manager** written in **pure Java (Swing)** with a clean UI.  
All passwords are stored **strongly encrypted** on disk and protected by a **master password**.

No cloud.  
No tracking.  
No dependencies.

---

## ✨ Features

- 🔒 **AES-256-GCM encryption** (authenticated, modern)
- 🔑 **Master password protection** (PBKDF2 + salt)
- 🧠 **Offline vault** (`vault.dat`)
- 🎨 Clean **UI** (Swing, system look & feel)
- 🔁 Add / Remove password entries
- 🔐 Secure password generator
- ⚡ Double-click an entry → **copies password to clipboard**
- 🧩 Generate password → **optionally save directly**
- 🖥 Windows **EXE available via GitHub Releases** (no Java required)

---

## 🧠 How it works

- On first start, you **create a master password**
- A local encrypted file `vault.dat` is created
- On next start, the vault is **unlocked using the master password**
- Without the correct master password, the data is **cryptographically unreadable**

Encryption details:
- **AES-256-GCM**
- **PBKDF2WithHmacSHA256**
- Random **salt + IV**
- Integrity protection included (tamper detection)

---
## 📁 Project Structure

## ▶️ Run from source

### Requirements
- Java **17+**
- Eclipse (or any Java IDE)

### Start
Run:
---

## 📦 Releases (Windows EXE)

Prebuilt **Windows `.exe`** files are available under  
👉 **GitHub → Releases**

The EXE is built using `jpackage` and includes a bundled JRE,  
so **no Java installation is required**.

---

## 🔐 Security notes

- `vault.dat` is **never committed** to Git
- The repository contains **source code only**
- The master password is **never stored**
- If you lose your master password, **the vault cannot be recovered**

---

## 🚧 Planned Improvements

- ⏱ Auto-lock after inactivity
- 📋 Clipboard auto-clear (timer)
- 🔍 Search / filter
- 🌙 Dark Mode
- 🔁 Change master password
- 🔏 Code signing (Windows / macOS)

---

## ⚠ Disclaimer

This project is intended for **personal use and educational purposes**.  
Use at your own risk. No warranty is provided.

---

## 📜 License

MIT License
