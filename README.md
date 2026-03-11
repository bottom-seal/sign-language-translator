# 🤟 Taiwan Sign Language (TSL) Real-Time Translation System

[![Project Type](https://img.shields.io/badge/Project-NCUE_Bachelor_Graduate-blue.svg)](https://www.ncue.edu.tw/)
[![Environment](https://img.shields.io/badge/Developed_in-Android_Studio-3DDC84.svg)](https://developer.android.com/studio)

本專案為國立彰化師範大學（NCUE）資訊工程系學士學位畢業專案，旨在利用機器視覺與深度學習開發一套台灣手語即時辨識系統。

---

## ⚠️ Important Note (檔案說明)

本儲存庫中僅包含經過 AI 篩選具代表性的核心程式碼與文件，用於展示系統邏輯與架構。
**請注意：本專案執行時所需的預訓練模型檔案 (`.h5` 或 `.tflite`) 以及 SQLite 資料庫檔案並未包含在此。** 若需完整執行，需自行根據論文架構重新訓練模型並建立資料庫。

---

## ✨ Key Features

* **Real-Time TSL Recognition**: 系統利用手機鏡頭即時捕捉手勢特徵，並將其翻譯為文字。
* **Regional Dialect Support**: 針對台灣手語「北、中、南」的地區性差異進行處理。
* **Integrated Dictionary**: 提供含括南北差異的手語字典，輔助使用者學習與查詢。
* **Instant Translation Chatroom**: 結合手語辨識與對話功能，並能保存對話紀錄於本地資料庫中。

---

## 🛠️ System Architecture

系統採用機器視覺與深度學習的雙軌架構，確保在行動裝置上的即時效能。

### 1. Data Pipeline
* **Landmark Extraction**: 使用 MediaPipe 追蹤雙手共 42 個關鍵點的 3D 座標。

* **Feature Processing**: 將連續 70 影格的動作序列作為模型輸入，以捕捉動態手語語義。

### 2. Deep Learning Model
* **Architecture**: 基於雙向長短期記憶網路 (**Bi-LSTM**) 構建。

* **Performance**: 針對 50 個常用單詞測試，辨識準確度可達 96% 以上。

---

## 🚀 Technical Stack

| Category | Tools/Technologies |
| :--- | :--- |
| **Development IDE** | **Android Studio** |
| **Language** | Python 3.8 (Training), Java/Kotlin (Android) |
| **Frameworks** | TensorFlow 2.4.1, Keras |
| **CV Library** | MediaPipe 0.8.5, OpenCV |
| **Database** | SQLite |

---

## 📊 Experimental Results

實驗結果顯示，在 PC 端推論速度可達 26+ FPS，能有效支援即時通訊需求。

* **Precision**: 97.27%
* **F1-score**: 96.84%
---

## 🌍 English Version / 英文版說明
*Click the button below to expand the 1:1 English translation.*

<details>
<summary><b>🇺🇸 Expand English Version (1:1 Translation)</b></summary>

# 🤟 Taiwan Sign Language (TSL) Real-Time Translation System

This project is a Bachelor's Graduation Project at the Department of Computer Science and Information Engineering, National Changhua University of Education (NCUE), aimed at developing a real-time TSL recognition system using computer vision and deep learning.

---

## ⚠️ Important Note

This repository contains core source code and documentation selected by AI to demonstrate the system architecture and logic.
**Please Note: The pre-trained model files (`.h5` or `.tflite`) and SQLite database files required for execution are NOT included here.** To fully run the project, you must retrain the model and establish the database based on the thesis architecture.

---

## ✨ Key Features

* **Real-Time TSL Recognition**: Captures hand gesture features via mobile camera and translates them into text instantly.
* **Regional Dialect Support**: Addresses regional variations in Taiwan Sign Language across "North, Central, and South."
* **Integrated Dictionary**: Provides a TSL dictionary covering North-South differences to assist users in learning and querying.
* **Instant Translation Chatroom**: Combines TSL recognition with chat functionality and saves conversation history in a local database.

---

## 🛠️ System Architecture

The system utilizes a dual-track architecture of computer vision and deep learning to ensure real-time performance on mobile devices.

### 1. Data Pipeline
* **Landmark Extraction**: Uses MediaPipe to track 3D coordinates of 42 key points across both hands.
* **Feature Processing**: Inputs continuous 70-frame motion sequences into the model to capture dynamic TSL semantics.

### 2. Deep Learning Model
* **Architecture**: Built upon a Bidirectional Long Short-Term Memory (**Bi-LSTM**) network.
* **Performance**: Achieved over 96% recognition accuracy across a test set of 50 common words.

---

## 🚀 Technical Stack

| Category | Tools/Technologies |
| :--- | :--- |
| **Development IDE** | **Android Studio** |
| **Language** | Python 3.8 (Training), Java/Kotlin (Android) |
| **Frameworks** | TensorFlow 2.4.1, Keras |
| **CV Library** | MediaPipe 0.8.5, OpenCV |
| **Database** | SQLite |

---

## 📊 Experimental Results

Experimental results show inference speeds of 26+ FPS on PC, effectively supporting real-time communication needs.

* **Precision**: 97.27%
* **F1-score**: 96.84%

</details>
