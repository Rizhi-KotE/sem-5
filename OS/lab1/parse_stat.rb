require 'csv'
require 'strscan'
HeaderPattern = /^.*(PID|#).*$/
def get_header strs
	s = StringScanner.new strs
	s.scan_until HeaderPattern
	return s.matched
end
def get_header strs, header_pattern
	s = StringScanner.new strs
	s.scan_until header_pattern
	return s.matched
end
def header_to_csv str
	s = StringScanner.new str
	s.skip_until /#/
	str = s.rest
	str.split.to_csv
end
def get_data_lines strs
	data_lines_indexes = []
	strs.each_with_index do |line, i|
		data_lines_indexes.push i+1 if line =~ /PID|#/
	end
	strs.select.with_index do |line, i| 
		data_lines_indexes.include? i
	end
end
def get_data_lines strs, pattern
	data_lines_indexes = []
	strs.each_with_index do |line, i|
		data_lines_indexes.push i+1 if line =~ pattern
	end
	strs.select.with_index do |line, i| 
		data_lines_indexes.include? i
	end
end
def line_to_csv str
	arr = str.split
	arr.to_csv
end
def arr_to_table arr
	s = ""
	arr.each {|line| s.concat line.to_s}
	return s
end