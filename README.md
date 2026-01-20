# 🧬 Conway's Game of Life (Java Swing)

A fully interactive implementation of **Conway's Game of Life** built in Java. This project features a custom multithreaded scheduler for simulation control, a dynamic GUI, and pattern placement tools.

## 🚀 Features

* **Simulation Control:** Start, Pause, Resume, and Clear the board.
* **Speed Control:** Adjustable simulation speed (Speed Up / Slow Down) using a custom threaded scheduler.
* **Interactive Grid:**
    * Click to toggle individual cells.
    * **Pattern Preview:** Hover over the grid to see a "ghost" preview of where a pattern will be placed before clicking.
* **Preset Patterns:** Quickly spawn famous automata structures:
    * *Glider*
    * *Blinker*
    * *Block*
    * *Crosshair*
* **Visuals:** Includes a "Random Color" mode that assigns unique colors to living cells.

## 🛠️ Technical Highlights

* **MVC Architecture:** clearly separates the Simulation Logic (`modele`), the GUI (`vue_controleur`), and the Threading.
* **Multithreading:** Uses a custom `Ordonnanceur` class (extending `Thread`) to manage the game loop independent of the Swing Event Dispatch Thread (EDT).
* **Observer Pattern:** The `FenetrePrincipale` implements `Observer` to automatically redraw the grid whenever the `Environnement` state changes.
* **Algorithmic Logic:** The `Case` (Cell) class calculates its next state based on the 8 surrounding neighbors using standard Game of Life rules (Underpopulation, Overcrowding, Reproduction).

## 📂 Project Structure

```text
src/
├── modele/              # Core logic
│   ├── Environnement.java   # The grid container and state manager
│   ├── Case.java            # Individual cell logic
│   ├── Ordonnanceur.java    # Thread scheduler for game speed
│   └── Point.java           # Coordinate helper
├── vue_controleur/      # User Interface
│   └── FenetrePrincipale.java # Main JFrame and Mouse Listeners
└── Main.java            # Entry point
