#!/bin/sh
mkdir -p "${PROJECT_DIR}/_Frameworks"
rsync -r /System/Library/PrivateFrameworks/MobileDevice.framework "${PROJECT_DIR}/_Frameworks" --exclude=XPCServices --links

