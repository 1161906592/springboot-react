function parseUrlSearch(search) {
  const result = {};
  if (search.startsWith("?")) {
    const contentStr = search.substr(1);
    if (contentStr) {
      contentStr.split("&").forEach(str => {
        const searchItem = str.split("=");
        result[searchItem[0]] = searchItem[1];
      });
    }
  }
  return result;
}

export default parseUrlSearch;
