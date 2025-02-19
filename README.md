# Battleship Game (JavaFX + Multiplayer Server)

## Overview

The **Battleship Game** is a multiplayer implementation of the classic **Battleship** game, developed in **Java** using **JavaFX** for the graphical user interface. The game follows the traditional rules of Battleship, where two players strategically place their ships and take turns guessing the location of their opponent's fleet.

This project consists of **two main components**:
- **Client Application**: The graphical interface for players, built using JavaFX.
- **Server Application**: Manages game sessions and synchronizes player actions.

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Gameplay](#gameplay)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## Features

✅ **Multiplayer Battleship Gameplay**  
✅ **JavaFX-Based Graphical User Interface**  
✅ **Client-Server Communication with Sockets**  
✅ **Real-Time Game Synchronization**  
✅ **Customizable Game Grid Size & Ships**  
✅ **Error Handling & Connection Recovery**  

---

## Technology Stack

### 🖥️ Client (JavaFX)
- **Java 11** (with JavaFX 12)
- **JavaFX Controls & FXML** (for UI)
- **Maven** (for dependency management)
- **JUnit 5** (for testing)

### ⚙️ Server (Java)
- **Java 11**
- **Socket Programming** (for multiplayer connectivity)
- **Maven**
- **JUnit 5** (for server-side testing)

---

## Installation

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/YourUsername/Battleship-Game.git
cd Battleship-Game
```

### 2️⃣ Install Client Dependencies
Navigate to the **client** directory and install dependencies:
```bash
cd client
mvn clean install
```

### 3️⃣ Install Server Dependencies
Navigate to the **server** directory and install dependencies:
```bash
cd ../server
mvn clean install
```

---

## Running the Application

### 🖥️ Start the Server
Run the following command inside the `server/` folder:
```bash
mvn exec:java -Dexec.mainClass="ServerGUI"
```
This will start the Battleship server, waiting for clients to connect.

### 🎮 Start the Client
Open a new terminal, navigate to the `client/` folder, and run:
```bash
mvn exec:java -Dexec.mainClass="ClientGame"
```
The **JavaFX-based game UI** should now launch, allowing players to join a game.

---

## Gameplay

1. **Players connect to the server** through the client application.
2. **Each player places their ships** on the grid before the game starts.
3. **Turn-based gameplay begins**:
   - Players take turns selecting grid positions to attack.
   - If a ship is hit, the grid updates accordingly.
   - The game continues until one player destroys all of the opponent's ships.
4. **Winner is announced** and the session resets for a new game.

---

## Project Structure

```
📂 Battleship-Game/
│
├── 📂 client/           # JavaFX client (game UI)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── ClientGame.java      # Main client application
│   │   │   │   ├── controllers/         # JavaFX controllers
│   │   │   │   ├── model/               # Game logic (grid, ships, hits)
│   │   │   ├── resources/               # UI assets (FXML, CSS)
│   │   ├── pom.xml                       # Maven dependencies
│
├── 📂 server/           # Server for multiplayer support
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── ServerGUI.java        # Server main class
│   │   │   │   ├── connections/          # Socket handling
│   │   │   │   ├── game/                 # Game session logic
│   │   ├── pom.xml                        # Maven dependencies
│
├── README.md            # Project documentation
└── .gitignore           # Files to ignore in version control
```

---

## Contributing

We welcome contributions! Follow these steps to contribute:

1. **Fork the Repository**  
   ```bash
   git fork https://github.com/YourUsername/Battleship-Game.git
   ```
2. **Create a Feature Branch**  
   ```bash
   git checkout -b feature/my-new-feature
   ```
3. **Commit Your Changes**  
   ```bash
   git commit -m "Add my new feature"
   ```
4. **Push to GitHub**  
   ```bash
   git push origin feature/my-new-feature
   ```
5. **Submit a Pull Request**  
   - Go to GitHub and create a **Pull Request** for review.

---
---

## 🚀 Future Enhancements

✅ **AI Opponent Mode** (for offline play)  
✅ **Spectator Mode** (view ongoing battles)  
✅ **Leaderboard System** (track player stats)  

---

This **Battleship Game** offers an exciting **multiplayer experience**, allowing players to compete over a **real-time networked game session** with a **smooth JavaFX user interface**. 🚢🔥

Happy gaming! 🎮💻
