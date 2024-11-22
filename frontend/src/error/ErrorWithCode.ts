export class ErrorWithCode extends Error {
  private _code: number;

  constructor(code: number, message: string) {
    super(message);
    this._code = code;
  }

  // Getter for the code property
  get code() {
    return this._code;
  }
}
