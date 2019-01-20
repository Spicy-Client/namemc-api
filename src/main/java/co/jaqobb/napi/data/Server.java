/*
 * This file is a part of napi, licensed under the MIT License.
 *
 * Copyright (c) Jakub Zagórski (jaqobb)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package co.jaqobb.napi.data;

import org.json.JSONArray;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Server {
	private final String address;
	private final Collection<UUID> likes;
	private final long cacheTime;

	public Server(String address, JSONArray array) {
		if (address == null) {
			throw new NullPointerException("address cannot be null");
		}
		if (array == null) {
			throw new NullPointerException("array cannot be null");
		}
		this.address = address;
		this.likes = IntStream.range(0, array.length()).boxed().map(index -> UUID.fromString(array.getString(index))).collect(Collectors.toUnmodifiableList());
		this.cacheTime = System.currentTimeMillis();
	}

	public String getAddress() {
		return this.address;
	}

	public Collection<UUID> getLikes() {
		return Collections.unmodifiableCollection(this.likes);
	}

	public boolean hasLiked(UUID uniqueId) {
		if (uniqueId == null) {
			throw new NullPointerException("uniqueId cannot be null");
		}
		return this.likes.contains(uniqueId);
	}

	public long getCacheTime() {
		return this.cacheTime;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || this.getClass() != object.getClass()) {
			return false;
		}
		Server server = (Server) object;
		return this.cacheTime == server.cacheTime &&
			Objects.equals(this.address, server.address) &&
			Objects.equals(this.likes, server.likes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.address, this.likes, this.cacheTime);
	}

	@Override
	public String toString() {
		return "Server{" +
			"address='" + this.address + "'" +
			", likes=" + this.likes +
			", cacheTime=" + this.cacheTime +
			"}";
	}
}