#!/usr/bin/env sh

./run-java.sh

# go install github.com/cespare/reflex@latest
reflex -r '\.java$' ./run-java.sh
