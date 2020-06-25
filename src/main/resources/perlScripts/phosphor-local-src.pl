#!/usr/bin/perl
srand;
rand($.) < 1 && ($line = $_) while <DATA>;
print $line;
__DATA__
