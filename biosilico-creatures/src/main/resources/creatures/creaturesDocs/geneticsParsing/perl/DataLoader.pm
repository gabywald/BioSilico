package DataLoader;

use strict;

sub loadConfigIni() {
	my $ini = "../data/sources.ini";
	
	my %conf;
	open (INI, "<".$ini) || die "Can't open $ini: $!\n";
	{
		my $section = undef;
		my $keyword = undef;
		my $value = undef;
		while (my $line = <INI>) {
			chomp($line);
			if ($line =~ /^\s*\[\s*(.+?)\s*\]\s*$/) {
				$section = $1;
			}
			if ($line =~ /^\s*([^=]+?)\s*[=:]\s*(.*?)\s*$/) {
				$conf{$section}->{$1} = $2;
			}
		}
	}
	close (INI);
	
	## foreach my $key (keys(%conf)) {
	## 	print "\t".$key." :: ".$conf{$key}."\n";
	## 	my %nexthash = %{$conf{$key}};
	## 	foreach my $okey (keys(%nexthash)) {
	## 		print "\t".$okey." :: ".$nexthash{$okey}."\n";
	## 	}
	## }
	
	return %conf;
}

sub loadFileConfig() {
	my $nameOfRSC = shift;
	my %configHash = loadConfigIni();
	my $value = $configHash{"paths"}->{ $nameOfRSC };
	my @toReturn = ();
	open (FILE, "<".$value) || die "Can't open $value: $!\n";
	{
		while (my $line = <FILE>) {
			chomp( $line );
			push (@toReturn, $line);
		}
	}
	close FILE ;
	return @toReturn;
}

sub loadDataConfig() {
	my $nameOfRSC = shift;
	my %configHash = loadConfigIni();
	my $value = $configHash{"data"}->{ $nameOfRSC };
	$value =~ s/\[ (.*) \]/$1/;
	return split(", ", $value);
}

1;