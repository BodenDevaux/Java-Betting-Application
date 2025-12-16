Project Title:
Betting Application

Description:
This is a Java-based betting application that allows users to place wagers on either a coin flip or a dice game. The project implements a client–server architecture with user authentication, persistent data storage, and multithreaded support for multiple simultaneous users.
The application features a fully interactive GUI and is designed to simulate a real-time betting environment where multiple clients can connect to a central server.

Installation Instructions:
  Prerequisites:
    Java JDK 8 or higher
    Git
  Steps:
  1. Clone repository : git clone https://github.com/YOUR_USERNAME/betting-application.git
  2. Open the project in your preferred IDE.
  3. Ensure all dependencies (if any) are properly configured.
  4. Compile the project.
  

Usage Instructions:
Start the server first:
  Run the server application (Server or equivalent main class).
  The server must be running before any clients attempt to connect.
Launch client instances:
  Run the client application.
  To allow multiple users, configure your IDE or system to run multiple instances of the client simultaneously.
User interaction:
  Users authenticate or create accounts.
  Place bets on either a coin flip or dice game.
  View results and updated balances in real time.

Features:
  Client–server architecture
  Coin flip and dice betting games
  User authentication system
  Persistent data storage
  Multithreaded server supporting multiple clients
  Interactive graphical user interface (GUI)
  Real-time wager processing

Technology Stack:
  Language: Java
  Networking: Java Sockets
  Concurrency: Multithreading
  GUI: Java Swing
  Data Persistence: SQLite (or file-based storage, if applicable)
  Architecture: MVC (Model–View–Controller)
