require "csv"
require "json"

filename = ARGV[0]
file = open filename
str = file.read
arr = JSON::parse str
csv_str = arr.map{|a| a.reverse.to_csv}.join "\n"

IO::write ARGV[1]||"#{filename}.csv", csv_str