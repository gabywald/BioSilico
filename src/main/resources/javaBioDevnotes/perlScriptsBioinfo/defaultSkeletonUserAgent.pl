#!/usr/bin/perl -w

use strict;

## ## ## this script is an example of use of HTTP user agent, 
## ## ## ## authentication, form use and parse
## ## ## ## use of arguments !! first is login, second is password
## ## ## ## ## OTHERS facultative arguments : opt+" "+value
## ## ## ## ## 	'-m' | '--menu' :: activate menu or not

use LWP::UserAgent;
use HTTP::Cookies;
use HTML::Parser;
use HTML::Form;

use Data::Dumper;

use Switch;

my $link = "http://www.ssii-lejeu.com/";

my @links = (
	"", ## 
);

print "Parsing arguments... ";
my $state = 0;
my %arguments = (
	'menu'	=> undef, ## activate or not menu
	'upDo'	=> undef, ## DESC / ASC
);
foreach my $arg (@ARGV) {
	print "\t'".$arg."'";
	switch($state) {
		case "0" { 
			$state = getStateValue($arg);
		}
		case "1" { $arguments{menu} = (($arg =~ /\-/)?undef:$arg);$state = 0; }
	}
	if ( ($arg =~ /\-/) && ($state eq "0") ) { $state = getStateValue($arg); }
	## ## print "\t".$arg."\t".$state."\n";
}
print "\n";

my $UA = getUserAgent();

## ## print "\t".$user."\t".$pswd."\n";
## ## print Dumper(\%arguments);die;

## ## ## ## ## 
## ## Exemples de User-Agent's : http://fr.wikipedia.org/wiki/User-Agent#Exemples
## ## voir aussi : http://www.anybrowser.org/
## ## voir aussi : http://fr.wikipedia.org/wiki/Fichier_d%27exclusion_des_robots
sub getUserAgent {
	my $cookiesFile = 'cookies.txt';
	if (-f $cookiesFile) {
		unlink($cookiesFile) 
			or die ("Erreur suppression Cookies\n");
	}
	## ## 'Mozilla/5.0 [en] (X11; i; Linux 2.2.16 i6876; Nav)'
	my $userAgent = LWP::UserAgent->new(
		agent		=> "Mozilla/5.0 [en] (X11; i; Linux 2.2.16 i6876; Nav) Sandbender 0.1 -- ".int(rand(100)),
		cookie_jar	=> HTTP::Cookies->new(
			file			=> $cookiesFile, 
			autosave		=> 1, 
			ignore_discard	=> 1, 
		)
	);
	return $userAgent;
}

sub submit {
	my $userAge = shift;
	my $request	= shift;
	my $result	= $userAge->request($request);
	## ## print "\t'".$result->is_success."'\n";
	if (!$result->is_success) { return $result->status_line; }
	## print Dumper(\$res);
	my $headers	= $result->headers();
	my $content	= $result->content();
	my %headers	= %{$headers};
	# print "\tHEADER : \n";
	# foreach my $key (keys %headers) 
	# 	{ print "\t\t".$key."\t".$headers{$key}."\n"; }
	# print "\n\n";
	## print "\tCONTENT\n";
	## print $content."\n\n";
	return $content;
}

sub connectTo {
	my $userAge = shift;
	my $link	= shift;
	my $subpage	= shift;
	if (!$subpage) { $subpage = ""; }
	## ## print "\t'".$link.$subpage."'\n";
	my $request = HTTP::Request->new( GET => $link.$subpage );
	return submit($userAge,$request);
}

sub describeForms {
	my $forms = shift;
	my @forms = @{$forms};
	foreach my $form (@forms) { 
		print $form."\n";
		my %form = %{$form};
		foreach my $key (sort keys %form) { 
			if ($key eq "inputs") {
				my @inputs = @{$form{$key}};
				print "\tINPUTS\n";
				foreach my $elt (@inputs) { 
					my %elt = %{$elt};
					print "\t\t\t".$elt{name}."\t".$elt{type}."\n";
					## foreach my $eky (sort keys %elt) { print "\t\t\t".$eky."\t".$elt{$eky}."\n"; } 
				}
			} else { print "\t".$key."\t".$form{$key}."\n"; }
		}
	}
}

## ## ## LOCAL FUNCTIONS
sub getStateValue {
	my $arg = shift;
	if ( ($arg eq '-m') || ($arg eq '--menu') ) 
		{ return 1; }
	elsif ( ($arg eq '-t') || ($arg eq '--type') ) 
		{ return 2; }
	return 0;
}

sub _applyMenu {
	## ## TYPE selection
	my $type = undef;
	if (defined $arguments{type}) { $type = $arguments{type}; }
	else {
		print "MENU\n";
		print "-----------------------------\n";
		print "\t0 :: \n";
		print "-----------------------------\n";
		$type = <STDIN>;
		chomp($type);
	}
	if ( (!defined $type) 
			|| ( ($type eq "undef") 
				|| ($type eq "") ) )
		{ $type = 0; }
	
	
}


