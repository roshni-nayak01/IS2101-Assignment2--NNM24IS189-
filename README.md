# Interrupt Controller Simulation in Java

## Overview
This project simulates an **Interrupt Controller** handling multiple I/O devices based on **priority** and **masking**. It demonstrates how **Keyboard, Mouse, and Printer** interrupts are served asynchronously using **Java multithreading**.

---

## Features
- **Devices & Priority:**  
  - Keyboard – High  
  - Mouse – Medium  
  - Printer – Low  

- **Interrupt Handling:**  
  - Serves the highest-priority interrupt first  
  - Supports masking/unmasking devices  
  - Logs ISR execution timestamps  

- **Asynchronous Simulation:**  
  - Device interrupts triggered randomly using threads  
  - Execution logs printed at the end  

---

## Running the Simulation
1. Compile and run:
    ```bash
    javac Main.java
    java Main
    ```
2. Observe output like:
    ```
    Keyboard → ISR Completed at HH:mm:ss
    Mouse Ignored (Masked)
    --- ISR Log ---
    HH:mm:ss - Keyboard executed
    HH:mm:ss - Mouse executed
    ```

---

## Key Concepts
- Enums for device priorities  
- Multithreading (`Thread`, `sleep()`)  
- Synchronized queue with `wait()` / `notify()`  
- Logging ISR execution timestamps  

---

## Author
- Name: *Roshni Arun Nayak*  
- USN: *NNM24IS189*   
