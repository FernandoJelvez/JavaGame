package io.github.levelMaker;

import io.github.engine.ButtonNames;
import io.github.engine.Controllable;

public interface ExtendedControlInterface extends Controllable {
    void pressExtended(ExtendedControlNames name);
    void releaseExtended(ExtendedControlNames name);
}
