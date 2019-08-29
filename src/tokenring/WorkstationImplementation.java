package tokenring;

import javax.xml.crypto.Data;
import java.util.LinkedList;
import java.util.Queue;

public class WorkstationImplementation extends Workstation {

	private NetworkInterface network;
	private LinkedList<Message> queue;
	private Frame frame;


	public WorkstationImplementation(NetworkInterface nic) {
            // TODO
		queue = new LinkedList<Message>();
		network = nic;
	}

	public NetworkInterface getNIC() {
            return network;
	}

	@Override
	public int compareTo(Workstation o) {
		if(this.getId() > o.getId()){
			return 1;
		}
		else if(this.getId() == o.getId()){
			return 0;
		}
		else{
			return -1;
		}
	}

	@Override
	public boolean equals(Object obj) {
		Workstation workObj = (Workstation)obj;
            if(this.id == workObj.id){
            	return true;
			}
            else{
				return false;
			}
	}

	public void sendMessage(Message m) {
		this.queue.add(m);
	}

	@Override
	public void tick() {
			// TODO

		if(this.getNIC().hasFrame()){
			frame = getNIC().read();

			if(frame.isTokenFrame()){
				if(queue.isEmpty()){
					getNIC().write(frame);
				}
				else{
					Message newMessage = queue.remove();
					DataFrame data = new DataFrame(newMessage);
					getNIC().write(data);
					incMsgSent();
				}
			}
			else if(frame.isDataFrame()){
				DataFrame dataFrame = (DataFrame) frame;
				Message newMessage = dataFrame.getMessage();
				if(newMessage.getReceiver() == this.getId()){
					newMessage.toString();
					getNIC().write(dataFrame);
					dataFrame.setReceived(true);
					incMsgRcvd();
				}
				else if(newMessage.getSender() == this.getId() && dataFrame.wasReceived()){
					newMessage.toString();
					getNIC().write(TokenFrame.TOKEN);
					incMsgDelivered();
				}
				else if(newMessage.getSender() == this.getId() && !dataFrame.wasReceived()){
					newMessage.toString();
					getNIC().write(TokenFrame.TOKEN);
				}
				else if(newMessage.getReceiver() != this.getId()){
					getNIC().write(dataFrame);
				}
			}
		}
	}
}
