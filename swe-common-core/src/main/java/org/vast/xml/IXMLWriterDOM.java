/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Initial Developer of the Original Code is SENSIA SOFTWARE LLC.
 Portions created by the Initial Developer are Copyright (C) 2012
 the Initial Developer. All Rights Reserved.

 Please Contact Alexandre Robin for more
 information.
 
 Contributor(s): 
    Alexandre Robin
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.xml;

import org.w3c.dom.Element;


/**
 * <p>
 * Interface for XML writer converting Java objects to a DOM element
 * </p>
 *
 * @author Alex Robin
 * @since Sep 30, 2012
 * * @param <ObjectType> Type of object this writer can serialize
 */
public interface IXMLWriterDOM<ObjectType>
{
    public Element write(DOMHelper dom, ObjectType obj) throws XMLWriterException;
}
