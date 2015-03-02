package comp442.lexical.state;

import java.io.IOException;

import comp442.lexical.InvalidCharacterException;
import comp442.lexical.LexableInputStream;
import comp442.lexical.token.Token;

public abstract class State {

	/**
	 * Process the stream, returning the next state
	 * 
	 * @param input The input stream to process
	 * @return The next state in the state machine
	 * @throws IOException if any stream operation throws an IOException
	 * @throws InvalidTokenException 
	 * @throws InvalidCharacterException 
	 */
	public abstract State process(LexableInputStream input) throws IOException, InvalidCharacterException;

	/**
	 * Check if this state is ready to produce a token
	 * 
	 * @return true if the state is ready to produce a token
	 */
	public boolean nextTokenReady(){
		return false;
	}

	/**
	 * Get the token generated after processing this state
	 * 
	 * @return The Token, or null if none is ready
	 */
	public Token getToken() {
		return null;
	}

	/**
	 * Determine whether the entire input stream was consumed by the last operation
	 * 
	 * @return true if the last call to process consumed the entire input stream, false otherwise
	 */
	public boolean done(){
		return false;
	}
	
	public State() {
	}
	
	@Override public String toString(){
		return this.getClass().getSimpleName();
	}
}
