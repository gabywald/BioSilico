#!/usr/bin/env perl
# $Id: dbfetch.pl 1546 2010-05-28 08:52:59Z hpm $
# ======================================================================
# WSDbfetch Perl client.
#
# Requires SOAP::Lite
#
# See:
# http://www.ebi.ac.uk/Tools/Webservices/services/dbfetch
# http://www.ebi.ac.uk/Tools/Webservices/clients/dbfetch
# http://www.ebi.ac.uk/Tools/Webservices/tutorials/soaplite
# ======================================================================
# WSDL URL for service
my $WSDL = 'http://www.ebi.ac.uk/ws/services/WSDbfetch?wsdl';

# Enable Perl warnings
use strict;
use warnings;

# Load libraries
use SOAP::Lite;
use Getopt::Long qw(:config no_ignore_case bundling);
use File::Basename qw(basename);

# Command-line options
my $trace;
GetOptions(
	'WSDL=s' => \$WSDL,     # Alternative WSDL URL
	'trace'  => \$trace,    # SOAP message trace
);

# If required enable SOAP message trace
if ($trace) {
	print STDERR "Tracing active\n";
	SOAP::Lite->import( +trace => qw(debug) );
}

# If no arguments have been specifed print usage and exit
my $argN = scalar(@ARGV);
if ( $argN < 1 ) {
	print STDERR 'Error: insufficent arguments specified', "\n";
	printUsage();
	exit(1);
}

# Create the service interface
my $soap = SOAP::Lite->service($WSDL)->proxy(
	'http://localhost/',

	#proxy => ['http' => 'http://your.proxy.server/'], # HTTP proxy
	timeout => 600,    # HTTP connection timeout
  )->on_fault(
	sub {              # SOAP fault handler
		my $soap = shift;
		my $res  = shift;

		# Throw an exception for all faults
		if ( ref($res) eq '' ) { die($res); }
		else { die( $res->faultstring ); }
		return new SOAP::SOM;
	}
  );

# Pocess command-line and perfom action
# List supported databases
if ( $ARGV[0] eq 'getSupportedDBs' ) {
	my $result = $soap->getSupportedDBs();
	foreach my $item (@$result) {
		print $item, "\n";
	}
}

# List supported databases and formats
elsif ( $ARGV[0] eq 'getSupportedFormats' ) {
	my $result = $soap->getSupportedFormats();
	foreach my $item (@$result) {
		print $item, "\n";
	}
}

# List supported databases and styles
elsif ( $ARGV[0] eq 'getSupportedStyles' ) {
	my $result = $soap->getSupportedStyles();
	foreach my $item (@$result) {
		print $item, "\n";
	}
}

# List supported formats for a database
elsif ( $ARGV[0] eq 'getDbFormats' && $argN == 2 ) {
	my $result = $soap->getDbFormats( $ARGV[1] );
	foreach my $item (@$result) {
		print $item, "\n";
	}
}

# List supported styles for a database and format
elsif ( $ARGV[0] eq 'getFormatStyles' && $argN == 3 ) {
	my $result = $soap->getFormatStyles( $ARGV[1], $ARGV[2] );
	foreach my $item (@$result) {
		print $item, "\n";
	}
}

# Fetch an entry
elsif ( $ARGV[0] eq 'fetchData' && $argN > 1 && $argN < 5 ) {
	my $query  = $ARGV[1];
	my $format = defined( $ARGV[2] ) ? $ARGV[2] : 'default';
	my $style  = defined( $ARGV[3] ) ? $ARGV[3] : 'raw';
	my $result = $soap->fetchData( $query, $format, $style );
	print $result, "\n" if($result);
}

# Fetch a set of entries
elsif ( $ARGV[0] eq 'fetchBatch' && $argN > 1 && $argN < 6 ) {
	my $db     = $ARGV[1];
	my $idList = $ARGV[2];
	if ( $idList eq '-' ) {    # Read from STDIN
		my $buffer;
		$idList = '';
		while ( sysread( STDIN, $buffer, 1024 ) ) {
			$idList .= $buffer;
		}
	}
	$idList =~ s/[ \t\n\r;]+/,/g;    # Id list should be comma seperated
	$idList =~ s/,+/,/g;             # Remove any empty items
	my $format = defined( $ARGV[3] ) ? $ARGV[3] : 'default';
	my $style  = defined( $ARGV[4] ) ? $ARGV[4] : 'raw';
	my $result = $soap->fetchBatch( $db, $idList, $format, $style );
	print $result;
}

# Unrecgnised method, or too many/few arguments so print usage
else {
	print STDERR
	  "Error: unrecognised method ($ARGV[0]) or too many arguments\n";
	printUsage();
	exit(2);
}

# Print usage message
sub printUsage {

	# Get the script filename for use in usage message
	my $scriptName = basename($0);

	# Print the message
	print <<EOF
Usage:
  $scriptName <method> [arguments...] [--trace] [--WSDL <wsdlUrl>]

A number of methods are available:

  getSupportedDBs - list available databases
  getSupportedFormats - list available databases with formats
  getSupportedStyles - list available databases with styles
  getDbFormats - list formats for a specifed database
  getFormatStyles - list styles for a specified database and format
  fetchData - retrive an database entry. See below for details of arguments.
  fetchBatch - retrive database entries. See below for details of arguments.

Fetching an entry: fetchData

  $scriptName fetchData <dbName:id> [format [style]]

  dbName:id  database name and entry ID or accession (e.g. UNIPROT:WAP_RAT)
  format     format to retrive (e.g. uniprot)
  style      style to retrive (e.g. raw)

Fetching entries: fetchBatch

  $scriptName fetchBatch <dbName> <idList> [format [style]]

  dbName     database name (e.g. UNIPROT)
  idList     list of entry IDs or accessions (e.g. 1433T_RAT,WAP_RAT).
             Maximum of 200 IDs or accessions. "-" for STDIN.
  format     format to retrive (e.g. uniprot)
  style      style to retrive (e.g. raw)

EOF
	  ;
}
