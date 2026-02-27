# Snake-Game-
A Java-based Snake Game with an exciting twist: the snake moves in a random zigzag pattern, eats food, collects bonuses, and grows dynamically. Designed using Java Swing for graphics and Timers for smooth gameplay.


🎮 Features

->Zigzag Movement: Snake randomly changes direction to make the game more challenging

->Bonus Items: Magenta blocks provide extra points (10–19) and temporary auto-growth.

->Dynamic Growth: Snake grows automatically when collecting bonuses.

->Score Tracking: Current score displayed in real-time.

->Game Over: Detects collisions with self; wraps around edges.

->Restart: Press R to restart the game after game over.

->Sound Effects: Plays sounds for eating, bonus, and game over events (requires .wav files).

🛠️ Built With

->Java

->Swing for GUI

->Timer & ActionListener for game loop

->LinkedList for snake body management

->Random for zigzag and food/bonus spawning

->SoundPlayer class for audio effects



📌 Gameplay Notes

The snake can wrap around the screen edges.

Bonuses trigger temporary auto-growth for faster score increase.

Avoid colliding with yourself; otherwise, it's Game Over.
