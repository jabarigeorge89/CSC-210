# Lab 08 — Processing Data
Jabari George

## Station 1 — CircleSizeSlider

a. The stateChanged() function in the ChangeListener runs when the slider value changes and updates the circle size.

b. The circle is redrawn when the slider changes and repaint() is called.

## Station 2 — Random Programs

a. Both programs obtain random data from the Random.org API. The data is returned in hexadecimal format.

b. The data is considered truly random because Random.org uses atmospheric noise rather than a standard pseudo-random number generator.

## Station 3 — LogisticMapGenerator

a. Growth rates between 0 and 1 lead to extinction, including a growth rate of 1 over time.

b. Growth rates greater than 1 and less than 3 generally lead to a stable population.

c. Growth rates above 3 and up to 4 can produce oscillating or chaotic populations.

I generated four CSV files using growth rates of 0.5, 2.5, 3.5, and 3.9.

## Station 4 — Static Graphs

I changed the graph color in GraphFromCSV from black to blue. I also changed the graph color in CSVtoPNG from blue to red. Both programs were recompiled and tested successfully.

## Station 5 — Animated Visualization

I changed the timer interval from 100 milliseconds to 250 milliseconds. This made the animation slower. Unlike the static graphs, the animated graph uses a timer to display data points gradually.

## Station 6 — GUI Interface

a. I changed the graph color from blue to magenta and successfully recompiled and tested the program.

b. Clicking the Update button or changing the iterations slider triggers repaint() to redraw the graph.

c. My observations supported the original conclusions. Lower growth rates lead to extinction, moderate growth rates produce stable populations, and higher growth rates can produce oscillations or chaos.

## Station 7 — Bifurcation Diagram

I ran the bifurcation diagram and observed how population behavior changes with different growth rates. The diagram shows stable populations, period doubling, and chaotic behavior. I also exported the diagram as PNG and CSV files.