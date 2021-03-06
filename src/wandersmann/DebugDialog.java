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

package wandersmann;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author Christian Lins
 */
public class DebugDialog extends Form implements CommandListener {

	private Command cmdBack = new Command("Back", Command.BACK, 0);
	private WandersmannMIDlet midlet;

	public DebugDialog(WandersmannMIDlet midlet) {
		super("Debug");
		this.midlet = midlet;
		setCommandListener(this);
		addCommand(cmdBack);
	}

	public void addMessage(String label, String msg) {
		this.insert(0, new StringItem(label + ": ", msg));
		while(this.size() > 50) {
			delete(50);
		}
	}

	public void commandAction(Command command, Displayable displayable) {
		if(command.equals(this.cmdBack)) {
			Display.getDisplay(midlet).setCurrent(midlet.getMap());
		}
	}

	public void show() {
		Display.getDisplay(midlet).setCurrent(this);
	}

}
