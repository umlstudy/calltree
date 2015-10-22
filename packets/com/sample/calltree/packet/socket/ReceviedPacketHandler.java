package com.sample.calltree.packet.socket;

import com.sample.calltree.packet.Packet;

public interface ReceviedPacketHandler {
	void packetReceived(Packet receivedPacket) throws Exception;
}
