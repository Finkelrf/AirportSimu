import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

class Tower {

		public ArrayList<Plane> planeList;
		private boolean planeWaitingToLand;
		private ArrayList<Integer> takeOffList;
		private ArrayList<Integer> landList;
		
		public int getLandListSize(){
			return landList.size();
		}
		
		public int getTakeOffList(){
			return takeOffList.size();
		}		
		
		public void addToTakeOffList(int id){
			takeOffList.add(id);
		}
		
		public void addToLandList(int id){
			landList.add(id);
		}
		
		public void rmFirstFromTakeOffList(){
			if(!takeOffList.isEmpty())
				takeOffList.remove(0);
		}
		
		public void rmFirstFromLandList(){
			if(!landList.isEmpty())
				landList.remove(0);
		}
		
		public boolean isTakeOffListEmpty(){
			return takeOffList.isEmpty();
		}
		
		public boolean isLandListEmpty(){
			return landList.isEmpty();
		}
		
		public int getFirstLandList(){
			if(!landList.isEmpty()){
				return landList.get(0);
			}else
				return -1;
		}
		
		public int getFirstTakeOffList(){
			if(!takeOffList.isEmpty()){
				return takeOffList.get(0);
			}else
				return -1;
		}

		public Tower() {
			this.planeList = new ArrayList<Plane>();
			this.planeWaitingToLand = false;
			this.landList = new ArrayList<Integer>();
			this.takeOffList= new ArrayList<Integer>();
		}
		
		public boolean isPlaneWaitingToLand(){
			return planeWaitingToLand;
		}

		// Give a number to a arriving plane
		public int registerNewAproache(Airport a) {
			this.planeList.add(new Plane(Motor.getNowDate()));
			planeWaitingToLand = true;
			a.getTrack().setDelayToLand();
			int id = planeList.get(planeList.size() - 1).setId(planeList.size()-1);
			return id;
		}

		public int fligthListSize() {
			return planeList.size();
		}
		
		public int sendPermissionToLand(){
			for (int i = 0; i < planeList.size(); i++) {
				if(planeList.get(i).getState() == Plane.PLANE_STATE.AIR){
					return planeList.get(i).getId();
				}
			}
			return -1;
		}

		public Plane getPlaneById(int planeId) {
			for (int i = 0; i < planeList.size(); i++) {
				int id = planeList.get(i).getId();
				if(id == planeId)
					return planeList.get(i);
			}
			System.out.println("Plane not found");
			return null;
		}

		public void notifyTakeOff(Plane p) {
			//remove plane 
			//planeList.remove(p);
			//p.writeLog();
			//save log
		}

	}
	
	