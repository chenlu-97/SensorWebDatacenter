/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gmd;


public interface Factory
{
    
    public CIResponsibleParty newCIResponsibleParty();
    
    
    public CICitation newCICitation();
    
    
    public CIAddress newCIAddress();
    
    
    public CIOnlineResource newCIOnlineResource();
    
    
    public CIContact newCIContact();
    
    
    public CITelephone newCITelephone();
    
    
    public CIDate newCIDate();
    
    
    public CISeries newCISeries();
    
    
    public MDKeywords newMDKeywords();
    
    
    public MDIdentifier newMDIdentifier();
    
    
    public MDConstraints newMDConstraints();
    
    
    public MDLegalConstraints newMDLegalConstraints();
    
}
