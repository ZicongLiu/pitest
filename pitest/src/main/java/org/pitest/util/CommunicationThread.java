/*
 * Copyright 2011 Henry Coles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.pitest.functional.SideEffect1;

public class CommunicationThread {

  private final SideEffect1<SafeDataOutputStream> sendInitialData;
  private final ReceiveStrategy                   receive;
  private final ServerSocket                      socket;
  private FutureTask<ExitCode>                    future;

  public CommunicationThread(final ServerSocket socket,
      final SideEffect1<SafeDataOutputStream> sendInitialData,
      final ReceiveStrategy receive) {
    this.socket = socket;
    this.sendInitialData = sendInitialData;
    this.receive = receive;
  }

  public void start() throws IOException, InterruptedException {
    future = createFuture();
  }

  private FutureTask<ExitCode> createFuture() {
    final FutureTask<ExitCode> future = new FutureTask<ExitCode>(new SocketReadingCallable(
        socket, sendInitialData, receive));
    final Thread thread = new Thread(future);
    thread.setDaemon(true);
    thread.setName("pit communication");
    thread.start();
    return future;
  }

  public ExitCode waitToFinish() throws InterruptedException,
      ExecutionException {
    return this.future.get();
  }

}
