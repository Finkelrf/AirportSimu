

import java.util.ArrayList;

public class Airport {
	
	public enum TrackState{
		TRACK_AVAILABLE,
		TRACK_NO_AVAILABLE
	}
	
	public enum GateState{
		GATE_AVAILABLE,
		GATE_NO_AVAILABLE
	}
	
	public enum TaxiwayState{
		TAXIWAY_AVAILABLE,
		TAXIWAY_NO_AVAILABLE		
	}
	
	private TrackState track;
	private GateState[] gates = new GateState[6];
	private TaxiwayState taxiways[] = new TaxiwayState[2];
	private Tour tour;
	
	public Airport() {
		this.track = TrackState.TRACK_NO_AVAILABLE;
		for(int i = 0; i <= 6; i++){
			gates[i] = GateState.GATE_NO_AVAILABLE;
		}
		
		for(int i = 0; i <= 6; i++){
			taxiways[i] = TaxiwayState.TAXIWAY_NO_AVAILABLE;
		}
		tour = new Tour();
	}	
	
	public void enableTrack(){
		this.track = TrackState.TRACK_AVAILABLE;
	}
	
	public void disableTrack(){
		this.track = TrackState.TRACK_NO_AVAILABLE;
	}
	
	public boolean trackIsAvailable(){
		return this.track == TrackState.TRACK_AVAILABLE;
	}
	
	public void enableGate(int gateNumber){
		gates[gateNumber] = GateState.GATE_AVAILABLE;
	}
	
	public void disableGate(int gateNumber){
		gates[gateNumber] = GateState.GATE_NO_AVAILABLE;
	}
	
	public boolean gateIsAvailable(int gateNumber){
		return gates[gateNumber] == GateState.GATE_AVAILABLE;
	}
	public void enabletaxiways(int taxiwayNumber){
		taxiways[taxiwayNumber] = TaxiwayState.TAXIWAY_AVAILABLE;
	}
	
	public void disabletaxiways(int taxiwayNumber){
		taxiways[taxiwayNumber] = TaxiwayState.TAXIWAY_NO_AVAILABLE;
	}
	
	public boolean taxiwaysIsAvailable(int taxiwayNumber){
		return taxiways[taxiwayNumber] == TaxiwayState.TAXIWAY_AVAILABLE;
	}

	public int fligthListSize(){
		return tour.fligthNumbers.size();
	}
	
 class Tour {
		
		private ArrayList<Integer> fligthNumbers;
		
		public Tour(){
			fligthNumbers = new ArrayList<Integer>();
		}
		
		public void registerNewAproache(int fligthNumber){
			this.fligthNumbers.add(fligthNumber);
		}
		
		public int fligthListSize(){
			return fligthNumbers.size();
		}
	}

}	
