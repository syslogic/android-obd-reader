package com.github.pires.obd.reader.interfaces;

import com.github.pires.obd.reader.model.ObdCommandJob;

public interface ObdProgressListener {
    void stateUpdate(final ObdCommandJob job);
}
