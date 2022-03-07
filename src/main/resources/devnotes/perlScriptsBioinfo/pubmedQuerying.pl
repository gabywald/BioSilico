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


    ## Search by PMID, example: http://www.ncbi.nlm.nih.gov/pubmed/18418893
    ## Search by Author, example: http://www.ncbi.nlm.nih.gov/sites/entrez?Db=pubmed&Cmd=Search&Term=%22Bonnet%20JE%22%5BAuthor%5D
    ## Search by Keywords, example: http://www.ncbi.nlm.nih.gov/pubmed?term=antioxidant%20chocolate
    ## get Abstract: http://www.ncbi.nlm.nih.gov/entrez/queryd.fcgi?db=pubmed&cmd=Retrieve&dopt=Abstract&list_uids=18461658&itool=pubmed_docsum
    ## get Related Articles: http://www.ncbi.nlm.nih.gov/entrez/queryd.fcgi?itool=pubmed_Abstract&db=pubmed&cmd=Display&dopt=pubmed_pubmed&from_uid=18276748
	
	## Search PubMed by author: http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&retstart=0&retmax=100&usehistory=y&retmode=xml&term=Sten%20H%20Vermund[author]
	## Search PubMed by keyword: http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&retstart=0&retmax=100&usehistory=y&retmode=xml&term=polymerase


my $link = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?";

my @links = (
	"http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?",	## default
	"http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?",	## General Search !!
	"http://www.ncbi.nlm.nih.gov/pubmed/",							## PMID search
	"http://www.ncbi.nlm.nih.gov/pubmed?term=",						## keyword search
	"http://www.ncbi.nlm.nih.gov/entrez/queryd.fcgi?db=pubmed&cmd=Retrieve&dopt=Abstract&itool=pubmed_docsum&list_uids=",		## getting abstracts [need ID('s)]
	"http://www.ncbi.nlm.nih.gov/entrez/queryd.fcgi?itool=pubmed_Abstract&db=pubmed&cmd=Display&dopt=pubmed_pubmed&from_uid=",	## getting related articleS from one ID
);

my %links = ();my $i = 0;
	$links{$i."::Default"}			= $links[$i];$i++;
	$links{$i."::General Search"}	= $links[$i];$i++;
	$links{$i."::PMID Search"}		= $links[$i];$i++;
	$links{$i."::Keyword Search"}	= $links[$i];$i++;
	$links{$i."::Asbtracts"}		= $links[$i];$i++;
	$links{$i."::Related Articles"}	= $links[$i];$i++;


## http://www.my-whiteboard.com/how-to-automate-pubmed-search-using-perl-php-or-java/
print "Parsing arguments... ";
my $state = 0;
my %arguments = (
	'type'			=> undef, ## main link selection !!
	'db'			=> undef, ## DataBase
	'tool'			=> undef, ## Tool
	'email'			=> undef, ## email (optionnal)
	'id'			=> undef, ## ID (separate by commas if needed)
	'retstart'		=> undef, ## sequential number of the first id retrieved – default=0 which will retrieve the first record
	'retmax'		=> undef, ## number of items retrieved – default=20
	'retmode'		=> undef, ## output format [html|text|xml|asn.1] (the 2 last not for journal)
	'rettype'		=> undef, ## output types based on database [uilist|abstract (not omim)|citation (not omim)|medline (not omim)|full (journals and omim)]
	'term'			=> undef, ## search term(s)
	'usehistory'	=> 'n',	  ## 
	'dopt'			=> undef, ## 
	'term'			=> undef, ##
	'filename'		=> undef, ##	
);
foreach my $arg (@ARGV) {
	print "\t'".$arg."'";
	switch($state) {
		case "0" { 
			$state = getStateValue($arg);
		}
		case "1" { $arguments{type}			= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "2" { $arguments{db}			= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "3" { $arguments{tool}			= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "4" { $arguments{email}		= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "5" { $arguments{id}			= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "6" { $arguments{retstart}		= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "7" { $arguments{retmax}		= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "8" { $arguments{retmode}		= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "9" { $arguments{rettype}		= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "10" { $arguments{term}		= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "11" { $arguments{usehistory}	= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "12" { $arguments{dopt}		= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "13" { $arguments{term}		= (($arg =~ /\-/)?undef:$arg);$state = 0; }
		case "14" { $arguments{filename}	= (($arg =~ /\-/)?undef:$arg);$state = 0; }
	}
	if ( ($arg =~ /\-/) && ($state eq "0") ) { $state = getStateValue($arg); }
	## ## print "\t".$arg."\t".$state."\n";
}
print "\n";

my $UA = getUserAgent();

my $mainType = _applyMenu();
$link = $links[$mainType];
_applySubMenu($mainType);
my $subPart = _createArgsList($mainType);

print "\n**********************\n";

if (!defined $subPart) { print "Bad arguments !";exit(0); }

my $content = connectTo($UA, $link, $subPart); ## "db=pubmed&rettype=full&retstart=0&retmax=10&term=herv"

my $filename = $arguments{filename};
if (!defined $filename) { $filename = "output.txt"; }

open (OUTPUT, ">".$filename) or die "file '".$filename."' not found !";
print OUTPUT $link.$subPart."\n\n";
print OUTPUT $content;

	switch($mainType) {
		case "0" {
			print OUTPUT "\n\n\t"."http://www.ncbi.nlm.nih.gov/".$arguments{db}."/".$arguments{id}."\n";
		}
		case "1" {

		}
		case "2" {

		}
		case "3" {

		}
		case "4" {

		}
		case "5" {

		}
	}
	
close OUTPUT;

print $content."\n**********************\n";

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
	print "\t'".$link.$subpage."'\n";
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
	if ( ($arg eq '-t') || ($arg eq '--type') ) 
		{ return 1; }
	elsif ( ($arg eq '-d') || ($arg eq '--db') ) 
		{ return 2; }
	elsif ( ($arg eq '-o') || ($arg eq '--tool') ) 
		{ return 3; }
	elsif ( ($arg eq '-e') || ($arg eq '--email') ) 
		{ return 4; }
	elsif ( ($arg eq '-i') || ($arg eq '--id') ) 
		{ return 5; }
	elsif ( ($arg eq '-a') || ($arg eq '--retstart') ) 
		{ return 6; }
	elsif ( ($arg eq '-m') || ($arg eq '--retmax') ) 
		{ return 7; }
	elsif ( ($arg eq '-r') || ($arg eq '--retmode') ) 
		{ return 8; }
	elsif ( ($arg eq '-y') || ($arg eq '--rettype') ) 
		{ return 9; }
	elsif ( ($arg eq '-s') || ( ($arg eq '--term') || ($arg eq '--search') ) ) 
		{ return 10; }
	elsif ( ($arg eq '-h') || ($arg eq '--usehistory') ) 
		{ return 11; }
	elsif ( ($arg eq '-p') || ($arg eq '--dopt') ) 
		{ return 12; }
	elsif ( ($arg eq '-d') || ($arg eq '--db') ) 
		{ return 13; }
	elsif ( ($arg eq '-f') || ($arg eq '--filename') ) 
		{ return 14; }
	return 0;
}




sub _applyMenu {
	## ## TYPE selection
	my $type = undef;
	if (defined $arguments{type}) { $type = $arguments{type}; }
	else {
		print "MENU\n";
		print "-----------------------------\n";
		foreach my $key (sort keys %links) {
			my @tabKey = split("::",$key);
			print $tabKey[0]." :: ".$tabKey[1]."\n";
		}
		print "-----------------------------\n";
		$type = <STDIN>;
		chomp($type);
	}
	if ( (!defined $type) 
			|| ( ($type eq "undef") 
				|| ($type eq "") ) )
		{ $type = 1; }
	return $type;
}


sub _applySubMenu {
	my $type = shift;
	switch($type) {
		case "0" {
			if (defined $arguments{db}) { ; }
			else {
				print "\tdb [pubmed|pmc|journals|omim]?\n";
				my $db = <STDIN>;
				chomp($db);
				if ( (!defined $db) || ( $db !~ /^(pubmed|pmc|journals|omim)$/) )
					{ $db = "pubmed"; }
				$arguments{db} = $db;
			}
			if (defined $arguments{tool})		{ ; }
			# else {
				# print "\ttool?\n";
				# my $tool = <STDIN>;
				# chomp($tool);
				# $arguments{tool} = $tool;
			# }
			if (defined $arguments{email})		{ ; }
			# else {
				# print "\temail?\n";
				# my $email = <STDIN>;
				# chomp($email);
				# $arguments{email} = $email;
			# }
			if (defined $arguments{id})			{ ; }
			else {
				print "\tid(s) [separate with commas]?\n";
				my $id = <STDIN>;
				chomp($id);
				if ( (!defined $id) || ( $id !~ /^[0-9]+(,[0-9]+)*$/) )
					{ $id = undef; }
				$arguments{id} = $id;
			}
			if (defined $arguments{retstart})	{ ; }
			# else {
				# print "\tret start?\n";
				# my $retstart = <STDIN>;
				# chomp($retstart);
				# if ( (!defined $retstart) || ( $retstart !~ /^[0-9]+$/) )
					# { $retstart = "0"; }
				# $arguments{retstart} = $retstart;
			# }
			if (defined $arguments{retmax})		{ ; }
			# else {
				# print "\tret max?\n";
				# my $retmax = <STDIN>;
				# chomp($retmax);
				# if ( (!defined $retmax) || ( $retmax !~ /^[0-9]+$/) )
					# { $retmax = "100"; }
				# $arguments{retmax} = $retmax;
			# }
			if (defined $arguments{retmode})	{ ; }
			else {
				print "\tret mode [xml|html|text|asn.1]?\n";
				my $retmode = <STDIN>;
				chomp($retmode);
				if ( (!defined $retmode) || ( $retmode !~ /^(xml|html|text|asn\.1)$/) )
					{ $retmode = "text"; }
				$arguments{retmode} = $retmode;
			}
			if (defined $arguments{rettype})	{ ; }
			else {
				print "\tret type [uilist|abstract*|citation*|medline*|full**]?\n";
				print "\t\tfor *OMIM : [docsum|synopsis|variants|detailed|ExternalLink]\n";
				my $rettype = <STDIN>;
				chomp($rettype);
				if ( (!defined $rettype) || ( $rettype !~ /^(uilist|abstract|citation|medline|full|docsum|synopsis|variants|detailed|ExternalLink)$/i) )
					{ $rettype = "uilist"; }
				$arguments{rettype} = $rettype;
			}
		}
		case "1" {
			if (defined $arguments{db}) { ; }
			else {
				print "\tdb [pubmed|pmc|journals|omim]?\n";
				my $db = <STDIN>;
				chomp($db);
				if ( (!defined $db) || ( $db !~ /^(pubmed|pmc|journals|omim)$/) )
					{ $db = "pubmed"; }
				$arguments{db} = $db;
			}
			## $arguments{db} = "pubmed";
			if (defined $arguments{retstart})	{ ; }
			else {
				print "\tret start?\n";
				my $retstart = <STDIN>;
				chomp($retstart);
				if ( (!defined $retstart) || ( $retstart !~ /^[0-9]+$/) )
					{ $retstart = "0"; }
				$arguments{retstart} = $retstart;
			}
			if (defined $arguments{retmax})		{ ; }
			else {
				print "\tret max?\n";
				my $retmax = <STDIN>;
				chomp($retmax);
				if ( (!defined $retmax) || ( $retmax !~ /^[0-9]+$/) )
					{ $retmax = "100"; }
				$arguments{retmax} = $retmax;
			}
			if (defined $arguments{retmode})	{ ; }
			else {
				print "\tret mode [xml|html|text|asn.1]?\n";
				my $retmode = <STDIN>;
				chomp($retmode);
				if ( (!defined $retmode) || ( $retmode !~ /^(xml|html|text|asn\.1)$/) )
					{ $retmode = "text"; }
				$arguments{retmode} = $retmode;
			}
			if (defined $arguments{rettype})	{ ; }
			else {
				print "\tret type [uilist|abstract*|citation*|medline*|full**]?\n";
				my $rettype = <STDIN>;
				chomp($rettype);
				if ( (!defined $rettype) || ( $rettype !~ /^(uilist|abstract|citation|medline|full)$/i) )
					{ $rettype = "uilist"; }
				$arguments{rettype} = $rettype;
			}
			if (defined $arguments{term})		{ ; }
			else {
				print "\tterm?\n";
				my $term = <STDIN>;
				chomp($term);
				$arguments{term} = $term;
			}
		}
		case "2" {
			if (defined $arguments{id})			{ ; }
			else {
				print "\tPMID?\n";
				my $id = <STDIN>;
				chomp($id);
				if ( (!defined $id) || ( $id !~ /^[0-9]+$/) )
					{ $id = undef; }
				$arguments{id} = $id;
			}
		}
		case "3" {
			if (defined $arguments{term})		{ ; }
			else {
				print "\tterm?\n";
				my $term = <STDIN>;
				chomp($term);
				$arguments{term} = $term;
			}
		}
		case "4" {
			if (defined $arguments{id})			{ ; }
			else {
				print "\tID's [separate with commas]?\n";
				my $id = <STDIN>;
				chomp($id);
				if ( (!defined $id) || ( $id !~ /^[0-9]+(,[0-9]+)*$/) )
					{ $id = undef; }
				$arguments{id} = $id;
			}
		}
		case "5" {
			if (defined $arguments{id})			{ ; }
			else {
				print "\tID?\n";
				my $id = <STDIN>;
				chomp($id);
				if ( (!defined $id) || ( $id !~ /^[0-9]+$/) )
					{ $id = undef; }
				$arguments{id} = $id;
			}
		}
	}
}

sub _createArgsList {
	my $type = shift;
	my $subLink = "";
	switch($type) {
		case "0" {
			$subLink .= "db=".$arguments{db};
			if (defined $arguments{tool})	{ $subLink .= "&tool=".$arguments{tool}; }
			if (defined $arguments{email})	{ $subLink .= "&email=".$arguments{email}; }
			if (defined $arguments{id})		{ $subLink .= "&id=".$arguments{id}; }
			else { $subLink = undef; }
			
			if (defined $arguments{retstart})	{ $subLink .= "&retstart=".$arguments{retstart}; }
			if (defined $arguments{retmax})		{ $subLink .= "&retmax=".$arguments{retmax}; }
			$subLink .= "&retmode=".$arguments{retmode};
			$subLink .= "&rettype=".$arguments{rettype};
		}
		case "1" {
			$subLink .= "db=".$arguments{db};
			$subLink .= "&retstart=".$arguments{retstart};
			$subLink .= "&retmax=".$arguments{retmax};
			$subLink .= "&retmode=".$arguments{retmode};
			$subLink .= "&rettype=".$arguments{rettype};
			$subLink .= "&term=".$arguments{term};
		}
		case "2" {
			if (defined $arguments{id})		{ $subLink .= $arguments{id}; }
			else { $subLink = undef; }
		}
		case "3" {
			if (defined $arguments{term})		{ $subLink .= $arguments{term}; }
			else { $subLink = undef; }
		}
		case "4" {
			if (defined $arguments{id})		{ $subLink .= $arguments{id}; }
			else { $subLink = undef; }
		}
		case "5" {
			if (defined $arguments{id})		{ $subLink .= $arguments{id}; }
			else { $subLink = undef; }
		}
	}
	# if (defined $subLink) { $subLink .= "&usehistory=n"; } # &cmd=display
	return $subLink;
}