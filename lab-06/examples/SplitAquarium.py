
from pathlib import Path
import re

source = Path("AquariumSim.java").read_text(encoding="utf-8")
folder = Path("AquariumSim")
folder.mkdir(exist_ok=True)

names = [
    "AquariumSim", "AquariumPanel", "Species",
    "Fish", "Pellet", "Poop", "Corpse",
    "Seaweed", "Point2D"
]

# Match actual top-level class declarations.
pattern = re.compile(
    r"(?m)^(?:public\s+)?class\s+("
    + "|".join(names) + r")\b"
)

matches = list(pattern.finditer(source))
found = [m.group(1) for m in matches]

if found != names:
    raise RuntimeError(f"Found classes: {found}")

header = source[:matches[0].start()]

for match in matches:
    name = match.group(1)

    # Find the opening brace for this class.
    start = source.find("{", match.end())
    if start == -1:
        raise RuntimeError(f"No opening brace for {name}")

    # Track braces to find the class's closing brace.
    depth = 1
    pos = start + 1

    while depth > 0 and pos < len(source):
        if source[pos] == "{":
            depth += 1
        elif source[pos] == "}":
            depth -= 1
        pos += 1

    if depth != 0:
        raise RuntimeError(f"Unmatched braces in {name}")

    body = source[match.start():pos]

    destination = folder / f"{name}.java"
    destination.write_text(
        header + body + "\n",
        encoding="utf-8"
    )

    print(f"Created {name}.java")

print("Finished splitting all classes!")
