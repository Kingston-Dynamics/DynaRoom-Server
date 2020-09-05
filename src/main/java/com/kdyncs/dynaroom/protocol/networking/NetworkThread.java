/*
 * Copyright (C) 2020 Kingston Dynamics Inc.
 * 
 * This file is part of DynaRoom
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kdyncs.dynaroom.protocol.networking;

public abstract class NetworkThread implements Runnable{

    protected Thread thread;
    protected boolean running;

    public void start() {

        if (thread.isAlive()) {
            return;
        }

        running = true;
        thread.start();
    }

    public void stop() {

        if (!thread.isAlive()) {
            return;
        }

        running = false;
        thread.interrupt();
    }
}
