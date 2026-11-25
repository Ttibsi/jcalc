#!/usr/bin/python3

import subprocess
import os

lines = subprocess.run(
    ["git", "log", "--oneline", "--reverse"],
    capture_output=True,
    text=True
).stdout.split("\n")

for idx, line in enumerate(lines):
    git_hash = line[:7]
    
    diff = subprocess.run(
        ["git", "show", git_hash],
        capture_output=True,
        text=True
    ).stdout.split("\n")
    print(diff)

    file_name = f"{idx}.patch" 
    with open(file_name, "w") as f:
        for diff_line in diff:
            f.write(diff_line + "\n")

    subprocess.run(
        ["scp", file_name, f"pi@192.168.1.7:/home/pi/{file_name}"],
    )
    os.remove(file_name)

    print(f"diff {idx} written for hash {git_hash}")
    print(f"\t{line[8:]}")


