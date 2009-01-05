/*
Copyright (c) 2008~2009, Justin R. Bengtson (poopshotgun@yahoo.com)
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
        this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
        this list of conditions and the following disclaimer in the
        documentation and/or other materials provided with the distribution.
    * Neither the name of Justin R. Bengtson nor the names of contributors may
        be used to endorse or promote products derived from this software
        without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package ssw.visitors;

import ssw.components.*;

public class VMechFullRecalc implements ifVisitor {
    // this visitor does a full recalculation of the mech.  This is usually done
    // when the mech is initialized or the techbase changes.

    public void LoadLocations(LocationIndex[] locs) {
        // does nothing here, but may later.
    }

    public void Visit(Mech m){
        // This method initializes and recalculates the entire mech.
        ifLoadout l = m.GetLoadout();
        l.FullUnallocate();

        // Recalculate the Engine and the multipliers
        m.GetEngine().SetRating( m.GetWalkingMP() * m.GetTonnage() );
        m.Recalculate();

        // Place the Engine and Gyro
        m.GetGyro().Place(l);
        m.GetEngine().Place(l);

        // Recalculate the Internal Structure
        m.GetIntStruc().ResetPlaced();
        m.GetIntStruc().Place(l);

        // Recalculate the Cockpit
        m.GetCockpit().Place(l);

        // Recalculate the Actuators
        m.GetActuators().PlaceActuators();
        
        // Recalculate the Heat Sinks
        m.GetHeatSinks().ReCalculate();
        
        // Recalculate the Jump Jets
        m.GetJumpJets().ReCalculate();
        
        // Recalculate the Physical Enhancements
        m.GetPhysEnhance().ResetPlaced();
        m.GetPhysEnhance().Place(l);
        
        // lastly, set the base cost
        m.ReCalcBaseCost();
    }
}
