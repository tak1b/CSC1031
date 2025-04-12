#!/bin/bash

for i in {1..9}; do
  if [ -d "w$i" ]; then
    mv "w$i" "w0$i"
  fi
done
