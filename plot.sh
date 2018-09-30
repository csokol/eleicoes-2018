#!/usr/bin/env bash

for d in `ls out/`; do
  cd out/$d
  cp ../../plot.gnuplot .
  gnuplot -c plot.gnuplot
  cd -
done
