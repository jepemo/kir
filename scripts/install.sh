#!/usr/bin/env bash

KBOOT_VERSION=0.1
KBOOT_DIR=kir-boot
KBOOT_DIST_DIR=$KBOOT_DIR/build/distributions
KBOOT_PKG=$KBOOT_DIR-$KBOOT_VERSION.tar

echo ""
echo "#    # ### ######  "
echo "#   #   #  #     # "
echo "#  #    #  #     # "
echo "###     #  ######  "
echo "#  #    #  #   #   "
echo "#   #   #  #    #  "
echo "#    # ### #     # "
echo ""
echo "Kotlin Web uFramework, Version: $KBOOT_VERSION"
echo ""

hash gradle 2>/dev/null || { echo >&2 "I require gradle to create the package.  Aborting."; exit 1; }



echo ">Creating Package to Install"
cd $KBOOT_DIR
gradle assembleDist
cd ..

tar -xvf $KBOOT_DIST_DIR/$KBOOT_PKG

echo ">Installing for everyone *"
echo "<Root Pass: "
sudo rm -Rf /usr/lib/$KBOOT_DIR
sudo mv $KBOOT_DIR-$KBOOT_VERSION /usr/lib/$KBOOT_DIR

echo ">Adding <kb> command to the Path"
sudo rm -Rf /usr/bin/kb
sudo ln -sf /usr/lib/$KBOOT_DIR/bin/$KBOOT_DIR /usr/bin/kb 

echo ">Installation complete. Type: "
echo -ne "\tkb help\n"



