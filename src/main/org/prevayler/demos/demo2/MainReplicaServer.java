// Prevayler(TM) - The Open-Source Prevalence Layer.
// Copyright (C) 2001-2003 Klaus Wuestefeld.
// This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License version 2.1 as published by the Free Software Foundation. This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details. You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.

package org.prevayler.demos.demo2;

import org.prevayler.demos.demo2.business.Bank;
import org.prevayler.demos.demo2.gui.BankFrame;
import org.prevayler.implementation.*;
import org.prevayler.implementation.log.TransactionLogger;
import org.prevayler.implementation.replica.PublishingServer;
import org.prevayler.util.clock.ClockActor;


public class MainReplicaServer {

	public static void main(String[] ignored) throws Exception {
		out(    "This demo shows how your application can be replicated"
			+ "\nwithout changing ONE SINGLE LINE OF CODE in the"
			+ "\nbusiness classes or GUI. This enables query load-"
			+ "\nbalancing and system fault-tolerance."
			+ "\n\nThe server is up. Now you can start the replica"
			+ "\non any machine in your network:"
			+ "\n  java org.prevayler.demos.demo2.MainReplica <This machine's IP Address>\n\n"
		);

		//Below are the three lines that were changed from Main.java (before calling the application code) to enable replication.
		TransactionPublisher publisher = new TransactionLogger("demo2Acid");
		SnapshotPrevayler prevayler = new SnapshotPrevayler(new Bank(), new SnapshotManager("demo2Acid"), publisher);
		new PublishingServer(publisher);
		new ClockActor(prevayler);

		new BankFrame(prevayler);
	}


	private static void out(String message) {
		System.out.println(message);
	}		

}