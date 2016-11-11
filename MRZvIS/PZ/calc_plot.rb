require "matrix"
require 'csv'
def activation x
	x > 0 ? 1:0
end
def sinaptic x_v, weights
	x_v * weights
end
def neiron_function x_v, weights
	activation neiron_function x_v, weights
end


class Network

	def initialize
		@first = load_weights '1-sloi.txt'
		@second = load_weights '2-sloi.txt'
		@third = load_weights '3-sloi.txt'
	end

	def load_weights filename
		matrix = CSV::read filename
		m = Matrix[*matrix].collect {|x| x.to_i}
	end

	def calc_out row_vector
		first = neiron_function row_vector, @first
		second = neiron_function row_vector, @second
		third = neiron_function row_vector, @third
	end		
end
