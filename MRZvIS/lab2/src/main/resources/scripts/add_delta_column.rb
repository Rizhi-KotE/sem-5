table = $stdin.read

table = table.split("\n").map.with_index do |row, i| 
  values = row.split.map {|value| value.to_f}
  values[2] = values[1]-values[0]
  values[3] = (values[2]/values[0]).abs
  values.unshift i
  values.map {|value| value.round 10}
end.map {|row| row.join " "}.join "\n"

$stdout.write table