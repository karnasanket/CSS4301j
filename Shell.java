import java.io.*;
import java.util.*;
class Shell extends Thread {
    public Shell( ) {
    }
        public void run( ) {
            for (int line = 1; ; line++) {
                String cmdLine = "";
                do {
                    StringBuffer inputBuf = new StringBuffer();
                    SysLib.cerr("shell[" + line + "]% ");
                    SysLib.cin(inputBuf);
                    cmdLine = inputBuf.toString();
                } while (cmdLine.length() == 0);
                String[] args = SysLib.stringToArgs(cmdLine);
                int first = 0;
                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals(";") || args[i].equals("&") || i == args.length - 1) {
                        String[] command = generateCmd(args, first, (i == args.length - 1) ? i + 1 : i);
                        if (command != null) {
                            // check if command[0] is “exit”. If so, get terminated
                            // otherwise, pass command to SysLib.exec( )
                            // if args[i]=“&” don’t call SysLib.join( ), Otherwise (i.e., “;”), keep calling SysLib.join( )
                            if (command[0].equals("exit")) {
                                SysLib.exit(); // if the commands is equal to "exit" then exit out of shell
                                return; // exit wakes up the parent, it doesnt stop the shell. this return satement
                                // makes it so that the final execution happens
                            }
                            else
                            {
                                SysLib.exec(command); // if a different command is called,
                                // then it gets executed by the SysLib.exec
                            }


                            if(args[i].equals("&")) // if arg is "&" then SysLib.join is not called
                            {
                            }
                            else
                            {
                                SysLib.join(); // else (when it is ";", it gets called
                            }

                        }
                        first = i + 1; // go on to the next command delimited by “;” or “&”
                    }
                }
            }
        }

        // method that takes the argument that the user inputs, and generates
        // it into a runnable command by the ThreadOS

        public String[] generateCmd(String args[], int first, int last)
        {
            if(last - first <= 0) // if no arguments were passed
            {
                return null;
            }
            String[] command = new String[last-first]; //length of the generatable commamd
                                                        // should be length of the args
            for (int i = first; i < last; i++)
            {
                command[i-first] = args[i]; // for loop to input the commands into the array
            }
            return command;
        }
    }
