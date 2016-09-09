require './parse_stat'
file_name = ARGV[0]
file = open file_name
file = file.read
header = get_header file
table = []
table.push header_to_csv header
lines = get_data_lines file.each_line
lines = lines.map {|line| line_to_csv line}
table.concat lines.to_ary
table = arr_to_table table
IO::write "#{file_name}#{'_scv'}.txt", table