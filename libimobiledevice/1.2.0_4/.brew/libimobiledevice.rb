class Libimobiledevice < Formula
  desc "Library to communicate with iOS devices natively"
  homepage "https://www.libimobiledevice.org/"
  revision 4

  stable do
    url "https://www.libimobiledevice.org/downloads/libimobiledevice-1.2.0.tar.bz2"
    sha256 "786b0de0875053bf61b5531a86ae8119e320edab724fc62fe2150cc931f11037"

    # Upstream commit for OpenSSL 1.1 compatibility
    patch do
      url "https://github.com/libimobiledevice/libimobiledevice/commit/13bf235c.diff?full_index=1"
      sha256 "be4cc20d11551e04ae51bfd797f154f47110c68b4363bdc4c1fe2b9f1c0667d5"
    end

    # Upstream commit for OpenSSL 1.1 compatibility
    patch do
      url "https://github.com/libimobiledevice/libimobiledevice/commit/02a0e03e.diff?full_index=1"
      sha256 "76a5eae502424fe7f59d64d526bf1d2d8736879ad03055c16f92cc5e0d6b8579"
    end
  end

  head do
    url "https://git.libimobiledevice.org/libimobiledevice.git"
    depends_on "autoconf" => :build
    depends_on "automake" => :build
    depends_on "libtool" => :build
    depends_on "libxml2"
  end

  depends_on "pkg-config" => :build
  depends_on "libplist"
  depends_on "libtasn1"
  depends_on "openssl@1.1"
  depends_on "usbmuxd"

  def install
    system "./autogen.sh" if build.head?
    system "./configure", "--disable-dependency-tracking",
                          "--disable-silent-rules",
                          "--prefix=#{prefix}",
                          # As long as libplist builds without Cython
                          # bindings, libimobiledevice must as well.
                          "--without-cython",
                          "--enable-debug-code"
    system "make", "install"
  end

  test do
    system "#{bin}/idevicedate", "--help"
  end
end
