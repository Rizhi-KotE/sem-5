require 'strscan'
filename = ARGV[0]
p filename
LinePattern = /.*$/
WordPattern = /\bw+\b/
DigitPatter = /\bd+\b/
def parse_header s
  s.skip_until /#/
  header = s.scan LinePattern
end
def get_matches str, regexp
  s = StringScanner.new str
  matches = []
  while s.exist? regexp do
    s.skip_until /\b/
    matches.push s.scan regexp
  end
end
file = open filename
str = file.read
s = StringScanner.new str
s.skip_until /#/
header = s.scan LinePattern
puts get_matches(header, WordPattern).to_s
