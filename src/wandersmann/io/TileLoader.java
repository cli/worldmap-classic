/*
 *  WANDERSMANN - J2ME OpenStreetMap Client
 *  see AUTHORS for a list of contributors.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  If you need a commercial license for this little piece of software,
 *  feel free to contact the author.
 */

package wandersmann.io;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Tile loading thread.
 * @author Christian Lins
 */
public class TileLoader extends Thread {
	
	private final Hashtable tasks = new Hashtable();

	public TileLoader() {
		super("TileLoader");
	}

	public void addTask(TileLoadingTask task) {
		synchronized(this.tasks) {
			tasks.put(task.URL, task);
			tasks.notify();
		}
	}

	public void run() {
		try {
			TileLoadingTask task = null;
			for(;;) {
				System.out.println("loop");
				synchronized(this.tasks) {
					Enumeration keys = tasks.keys();
					if(keys.hasMoreElements()) {
						task = (TileLoadingTask)this.tasks.remove(keys.nextElement());
						if(System.currentTimeMillis() - task.creationTime() > 30000) {
							// Skip this task as it is probably too old (> 30 seconds)
							continue;
						}
					}
				}

				if(task != null) {
					System.out.println("Running task " + task.URL);
					task.run();
					task = null;
				} else {
					synchronized(tasks) {
						tasks.wait();
					}
				}
			}
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("TileLoader thread ended.");
	}

}
