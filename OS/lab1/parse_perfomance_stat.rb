require './parse_stat'
require 'csv'

CpuHeaderPatter = /^.*%user.*$/
SwitchHeaderPatter = /^.*proc\/s.*$/
InterruptsSwitchHeaderPatter = /^.*INTR.*$/
QueueSwitchHeaderPatter = /^.*runq-sz.*$/

headers_pattern = []
headers_pattern.push CpuHeaderPatter, SwitchHeaderPatter, 
InterruptsSwitchHeaderPatter, QueueSwitchHeaderPatter

file = open 'witcher_zip'
str = file.read

headers = headers_pattern.map { |header| get_header str, header  }

headers_arr = headers.map do |header|
	arr = header.split
	arr[0] = "time"
	arr
end

headers_arr = headers_arr.join(" ").split

data = headers_pattern.map do |header|  
	lines = get_data_lines str.each_line, header
end

data_concat = data[0].map.with_index do |line, i|
	arr = data[0][i].split+data[1][i].split+data[2][i].split+data[3][i].split
end

data_concat.unshift headers_arr

csv = data_concat.map do |line| 
	line.to_csv  
end

IO::write "witcher_zip_csv.txt", csv.join