package comp442.lexical.token;

public enum TokenType {
	// reserved words
	tok_and,
	tok_or,
	tok_not,
	
	tok_if,
	tok_else,
	tok_then,
	tok_for,
	
	tok_class,
	
	tok_int,
	tok_float,
	
	tok_get,
	tok_put,
	
	tok_return,
	
	// identifier
	tok_id,
	
	//
	tok_int_literal,
	
	//
	tok_float_literal,
	
	//
	tok_equals,
	tok_assignment,
	tok_greater_than_equals,
	tok_greater_than,
	tok_less_than_equals,
	tok_less_than,
	tok_diamond,
	tok_plus,
	tok_minus,
	tok_star,
	tok_semicolon,
	tok_comma,
	tok_dot,
	tok_slash,
	
	//
	tok_open_paren,
	tok_open_brace,
	tok_open_square,
	tok_close_paren,
	tok_close_brace,
	tok_close_square,
}
