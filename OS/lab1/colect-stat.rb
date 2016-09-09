pid = ARGV[0]
time = ARGV[1]
time = 10 if time == nil
top_file = "top_stat_#{pid}"
pidstat_file = "pidstat_stat_#{pid}"
run_top = "top -b -d 1 -n #{time} -p #{pid} >> #{top_file}"
run_pidstat = "pidstat -hdrtu -p #{pid} 1 #{time} >> #{pidstat_file}"
system "#{run_top} & #{run_pidstat}"
puts 'end'