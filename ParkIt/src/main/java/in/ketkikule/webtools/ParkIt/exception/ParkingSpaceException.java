package in.ketkikule.webtools.ParkIt.exception;

public class ParkingSpaceException extends Exception {
	public ParkingSpaceException(String message) {
		super("ParkingSpaceException-" + message);
	}
	
	public ParkingSpaceException(String message, Throwable cause) {
		super("ParkingSpaceException-" + message, cause);
	}
}
