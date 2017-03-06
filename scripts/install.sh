#!/usr/bin/env bash

KBOOT_VERSION=0.1
KBOOT_DIR=kir-boot
KBOOT_DIST_DIR=$KBOOT_DIR/build/distributions
KBOOT_PKG=$KBOOT_DIR-$KBOOT_VERSION.tar

# Colors
BOLD='\033[1;37m'
RESET='\033[0m'

echo -ne "$BOLD\n"
echo -ne "#    # ### ######  \n"
echo -ne "#   #   #  #     # \n"
echo -ne "#  #    #  #     # \n"
echo -ne "###     #  ######  \n"
echo -ne "#  #    #  #   #   \n"
echo -ne "#   #   #  #    #  \n"
echo -ne "#    # ### #     # \n"
echo -ne "\n"
echo -ne "Kotlin Web uFramework, Version: $KBOOT_VERSION\n"
echo -ne "$RESET\n"

hash gradle 2>/dev/null || { echo >&2 "I require gradle to create the package.  Aborting."; exit 1; }



echo -ne "$BOLD> Creating Package to Install$RESET\n"
cd $KBOOT_DIR
gradle assembleDist
cd ..

tar -xvf $KBOOT_DIST_DIR/$KBOOT_PKG

echo -ne "$BOLD> Installing for everyone *$RESET\n"
echo -ne "$BOLD< Root Pass: $RESET"
sudo rm -Rf /usr/lib/$KBOOT_DIR
sudo mv $KBOOT_DIR-$KBOOT_VERSION /usr/lib/$KBOOT_DIR

echo -ne "\n$BOLD> Adding <kb> command to the Path$RESET\n"
sudo rm -Rf /usr/bin/kb
sudo ln -sf /usr/lib/$KBOOT_DIR/bin/$KBOOT_DIR /usr/bin/kb 

echo -ne "$BOLD> Installation complete. Type: $RESET\n"
echo -ne "\tkb help\n"



