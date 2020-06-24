#!/usr/bin/perl -w

use strict;

use LWP::UserAgent;
use HTTP::Cookies;
use HTML::Parser;
use HTML::Form;

use Data::Dumper;

use Switch;

## ## ... ... ... ... ... ... ... ... ... ... 

my $link = "http://creaturescaves.com/downloads.php?section=Creatures&game=C2&species=&sortBy=ID&availability=&gender=&searchFor=&page=";

my @links = ();

my $UA = getUserAgent();

## ## print "\t".$user."\t".$pswd."\n";
## ## print Dumper(\%arguments);die;

## ## AUTHENTICATION
## my $content = connectTo($UA,$links[int(rand(@links))],"");
## my @forms = HTML::Form->parse( $content, $link );
## describeForms(\@forms);
## my $form = $forms[0];
## $form->value( username => $user);
## $form->value( password => $pswd);
## submit($UA,$form->click());
## $content = connectTo($UA,$link,"index.php");
## ## print $content."\n";

for (my $pageIndex = 1 ; $pageIndex < 25 ; $pageIndex++) {
	print "\t".$link.$pageIndex."\n";
	my $content = connectTo($UA, $link.$pageIndex, "");
	my @content = split("\n", $content);
	foreach my $line (@content) {
		if ($line =~ /<TD bgcolor=".*?" width="20%" align="center"><A href="(.*?\.zip)"><IMG src="(.*?\.jpg)" border="0"><\/A><\/TD><TD bgcolor/) {
			print "\t\t".$1."\t".$2."\n";
			
			my $toDownloadZIP	= "http://creaturescaves.com".$1;## $toDownloadZIP =~ s/\/\//\//g;
			my $toDownloadJPG	= "http://creaturescaves.com".$2;## $toDownloadJPG =~ s/\/\//\//g;
			
			my $localeZIP		= $toDownloadZIP;$localeZIP =~ s/^.*\/(.*?)$/$1/;
			my $localeJPG		= $toDownloadJPG;$localeJPG =~ s/^.*\/(.*?)$/$1/;
			
			print "\t\t".$toDownloadZIP." => ".$localeZIP."\n";
			print "\t\t".$toDownloadJPG." => ".$localeJPG."\n";
			
			my $contentZIP = connectTo($UA, $toDownloadZIP, "");
			open (OUTPUT, ">".$localeZIP);print OUTPUT $contentZIP;close OUTPUT;
			my $contentJPG = connectTo($UA, $toDownloadJPG, "");
			open (OUTPUT, ">".$localeJPG);print OUTPUT $contentJPG;close OUTPUT;
			
		}
		
		if ($line =~ /<SPAN class="headline" ID="headline"><A href="downloads.php?section=Creatures&view=(.*?)">(.*?)<\/A><\/SPAN> &nbsp; <SPAN class="main" ID="main"><B>C1 Norn<\/B><\/SPAN> &nbsp; <SPAN class="note" ID="note"><B><A href="mycaves.php\?section=Users&view=(.*?)">(.*?)<\/A> \| (.*?)<\/B><\/SPAN><\/TD><TD width="27%" align="right" valign="top" class="note" ID="note"> &nbsp;<A href="downloads.php?section=Creatures&view=(.*?)"><IMG src="images\/design\/post_comment.png" width="18" height="18" alt="1 comment" title="1 comment"><\/A> &nbsp;<B><A href="downloads.php?section=Creatures&view=(.*?)">1<\/A><\/B> &nbsp;<IMG src="images\/design\/post_like.png" width="18" height="18" alt="log in to like post" title="log in to like post"><\/A><\/B><\/TD><\/TR><\/TABLE><BR><TABLE align="right" cellspacing="4" cellpadding="4" border="0"><TR><TD align="right"><SPAN class="note">gender<\/TD><TD><SPAN class="note" ID="note"><B>Male<\/B><\/SPAN><\/TD><\/TR><TR><TD align="right"><SPAN class="note">age<\/TD><TD><SPAN class="note" ID="note"><B>(.*?)<\/B><\/SPAN><\/TD><\/TR><TR><TD align="right"><SPAN class="note">generation<\/TD><TD><SPAN class="note" ID="note"><B>(.*?)<\/B><\/SPAN><\/TD><\/TR><\/TABLE><SPAN class="main" ID="main">(.*?)<\/SPAN><\/TD>/) {
			## print "\t\t".$1."\t".$2."\t".$3."\t".$4."\t".$5."\t".$6."\t".$7."\t".$8."\t".$9."\n";
		}
	}
}

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
	return submit($userAge, $request);
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


